package ru.cofffeen.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Operation {
    private Long id;
    private String type;
    private BigDecimal amount;
    private BigDecimal commission;
    private LocalDateTime timestamp;
    private Account account;
    private Account relatedAccount;
}
