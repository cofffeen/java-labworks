package ru.cofffeen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for creating a new admin")
public class CreateAdminRequest {
    @Schema(description = "Admin login", example = "admin", required = true)
    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 50, message = "Login size must be between 3 and 50 characters")
    private String login;

    @Schema(description = "Admin password", example = "admin123", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password size must be between 3 and 50 characters")
    private String password;

    @Schema(description = "Admin full name", example = "Admin", required = true)
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name can only contain letters")
    private String name;
}
