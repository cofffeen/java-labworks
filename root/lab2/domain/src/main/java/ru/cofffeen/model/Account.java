package ru.cofffeen.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Account {
    private Long id;
    private BigDecimal balance;
    private User owner;
    private List<Operation> operations;
}
