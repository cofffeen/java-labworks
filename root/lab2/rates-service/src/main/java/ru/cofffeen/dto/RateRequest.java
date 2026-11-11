package ru.cofffeen.dto;

import lombok.Data;

@Data
public class RateRequest {
    private String currency;
    private String requestId;
}
