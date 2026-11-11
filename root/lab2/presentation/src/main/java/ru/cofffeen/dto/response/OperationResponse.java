package ru.cofffeen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Operation information response")
public class OperationResponse {
    @Schema(description = "Operation ID", example = "1")
    private Long id;

    @Schema(description = "Operation type", example = "TRANSFER_OUT")
    private String type;

    @Schema(description = "Operation amount", example = "100.50")
    private BigDecimal amount;

    @Schema(description = "Commission amount", example = "3.00")
    private BigDecimal commission;

    @Schema(description = "Operation timestamp")
    private LocalDateTime timestamp;

    @Schema(description = "Account ID", example = "1")
    private Long accountId;

    @Schema(description = "Related account ID", example = "2")
    private Long relatedAccountId;
}