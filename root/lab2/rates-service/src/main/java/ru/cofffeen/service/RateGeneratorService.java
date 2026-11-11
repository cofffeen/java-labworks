package ru.cofffeen.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.cofffeen.dto.RateMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class RateGeneratorService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String RATES_TOPIC = "currency-rates";

    @Value("${rates.currencies}")
    private List<String> currencies;

    private final SecureRandom random = new SecureRandom();
    private final Map<String, BigDecimal> baseRates = new ConcurrentHashMap<>();

    @Scheduled(fixedDelayString = "${rates.update-interval-ms:10000}")
    public void publishRates() {
        for (String currency : currencies) {
            BigDecimal newRate = generateRate(currency);
            RateMessage message = RateMessage.builder()
                    .currency(currency)
                    .rateToRub(newRate)
                    .timestamp(LocalDateTime.now())
                    .build();

            kafkaTemplate.send(RATES_TOPIC, currency, message);
            log.info("Published rate for {}: {} RUB", currency, newRate);
        }
    }

    private BigDecimal generateRate(String currency) {
        double baseRate = getBaseRate(currency);
        double variation = (random.nextDouble() - 0.5) * 0.1;
        double newRate = baseRate * (1 + variation);

        baseRates.put(currency, BigDecimal.valueOf(baseRate));

        return BigDecimal.valueOf(newRate).setScale(4, RoundingMode.HALF_UP);
    }

    private double getBaseRate(String currency) {
        return switch (currency) {
            case "USD" -> 71.30;
            case "EUR" -> 82.72;
            default -> 1.0;
        };
    }

    public BigDecimal getCurrencyRate(String currency) {
        if (!baseRates.containsKey(currency)) {
            generateRate(currency);
        }
        return baseRates.getOrDefault(currency, BigDecimal.ONE);
    }
}
