package ru.cofffeen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Transfer operation response")
public class TransferResponse {
    @Schema(description = "Source account ID", example = "1")
    private Long fromAccountId;

    @Schema(description = "Destination account ID", example = "2")
    private Long toAccountId;

    @Schema(description = "Transfer amount", example = "100.50")
    private BigDecimal amount;

    @Schema(description = "Commission amount", example = "3.00")
    private BigDecimal commission;
}