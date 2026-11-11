package ru.cofffeen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for money transfer between accounts")
public class TransferRequest {
    @Schema(description = "Source account ID", example = "1", required = true)
    @NotNull(message = "From account ID is required")
    private Long fromAccountId;

    @Schema(description = "Destination account ID", example = "2", required = true)
    @NotNull(message = "To account ID is required")
    private Long toAccountId;

    @Schema(description = "Amount to transfer", example = "100.50", required = true)
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}