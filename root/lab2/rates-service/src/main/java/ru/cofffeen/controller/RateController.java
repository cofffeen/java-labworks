package ru.cofffeen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cofffeen.dto.RateResponse;
import ru.cofffeen.service.RateGeneratorService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {
    private final RateGeneratorService rateGeneratorService;

    @GetMapping("/{currency}")
    public RateResponse getRate(@PathVariable String currency, @RequestParam(required = false) String requestId) {
        BigDecimal rate = rateGeneratorService.getCurrencyRate(currency);

        return RateResponse.builder()
                .currency(currency.toUpperCase())
                .rateToRub(rate)
                .timestamp(LocalDateTime.now())
                .requestId(requestId)
                .build();
    }
}
