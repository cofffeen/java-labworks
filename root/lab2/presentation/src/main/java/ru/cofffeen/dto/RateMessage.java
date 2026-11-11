package ru.cofffeen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateMessage {
    private String currency;
    private BigDecimal rateToRub;
    private LocalDateTime timestamp;
}
