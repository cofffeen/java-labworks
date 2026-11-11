package ru.cofffeen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for creating a new account")
public class CreateAccountRequest {
    @Schema(description = "Owner user ID", example = "1", required = true)
    @NotNull(message = "User ID is required")
    private Long userId;
}