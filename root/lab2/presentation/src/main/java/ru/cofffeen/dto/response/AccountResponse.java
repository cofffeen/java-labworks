package ru.cofffeen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Account information response")
public class AccountResponse {
    @Schema(description = "Account ID", example = "1")
    private Long id;

    @Schema(description = "Current balance", example = "1000.50")
    private BigDecimal balance;

    @Schema(description = "Owner user ID", example = "1")
    private Long ownerId;

    @Schema(description = "Owner name", example = "John Doe")
    private String ownerName;

    @Schema(description = "Recent operations")
    private List<OperationResponse> recentOperations;
}