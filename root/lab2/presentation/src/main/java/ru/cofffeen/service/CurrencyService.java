package ru.cofffeen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.cofffeen.dto.RateMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final Map<String, RateMessage> rateCache = new ConcurrentHashMap<>();

    @Value("${rates.cache-ttl-ms:30000}")
    private long cacheTtlMs;

    @Value("${rates.request-timeout-ms:5000}")
    private long requestTimeoutMs;

    private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;
    private static final String RATES_TOPIC = "currency-rates";
    private static final String RATES_REQUEST_TOPIC = "currency-rate-requests";
    private static final String RATES_REPLY_TOPIC = "currency-rate-replies";

    @KafkaListener(topics = RATES_TOPIC, groupId = "bank-service-group")
    public void consumeRate(RateMessage rate) {
        rateCache.put(rate.getCurrency(), rate);
        log.info("Updated rate cache for {}: {}", rate.getCurrency(), rate.getRateToRub());
    }

    public BigDecimal convertToCurrency(BigDecimal amountRub, String targetCurrency) {
        if ("RUB".equalsIgnoreCase(targetCurrency)) {
            return amountRub;
        }

        BigDecimal rate = getRate(targetCurrency.toUpperCase());
        if (rate == null) {
            throw new RuntimeException("Rate not available for currency: " + targetCurrency);
        }

        return amountRub.divide(rate, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRate(String currency) {
        RateMessage cached = rateCache.get(currency);

        if (isRateValid(cached)) {
            log.debug("Using cached rate for {}: {}", currency, cached.getRateToRub());
            return cached.getRateToRub();
        }

        return requestRateViaKafka(currency);
    }

    private boolean isRateValid(RateMessage rate) {
        if (rate == null) return false;
        return Duration.between(rate.getTimestamp(), LocalDateTime.now()).toMillis() < cacheTtlMs;
    }

    private BigDecimal requestRateViaKafka(String currency) {
        try {
            String requestId = java.util.UUID.randomUUID().toString();

            Message<String> request = MessageBuilder.withPayload(currency)
                    .setHeader(KafkaHeaders.TOPIC, RATES_REQUEST_TOPIC)
                    .setHeader(KafkaHeaders.REPLY_TOPIC, RATES_REPLY_TOPIC)
                    .setHeader("requestId", requestId)
                    .build();

            RequestReplyFuture<String, Object, Object> future =
                    replyingKafkaTemplate.sendAndReceive((ProducerRecord<String, Object>) request);

            Message<Object> response = (Message<Object>) future.get(requestTimeoutMs, TimeUnit.MILLISECONDS);

            if (response != null && response.getPayload() instanceof RateMessage rateMsg) {
                rateCache.put(currency, rateMsg);
                log.info("Received rate via request-reply for {}: {}", currency, rateMsg.getRateToRub());
                return rateMsg.getRateToRub();
            }

            throw new RuntimeException("Empty response from rates-service");

        } catch (Exception e) {
            log.error("Failed to get rate for {} via Kafka: {}", currency, e.getMessage());
            throw new RuntimeException("Rate service unavailable for currency: " + currency);
        }
    }
}
