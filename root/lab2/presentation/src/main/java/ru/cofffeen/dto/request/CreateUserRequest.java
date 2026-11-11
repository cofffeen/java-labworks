package ru.cofffeen.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request for creating a new user")
public class CreateUserRequest {
    @Schema(description = "User login", example = "john_doe", required = true)
    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 50, message = "Login size must be between 3 and 50 characters")
    private String login;

    @Schema(description = "User password", example = "user123", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password size must be between 3 and 50 characters")
    private String password;

    @Schema(description = "User full name", example = "John Doe", required = true)
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name can only contain letters")
    private String name;

    @Schema(description = "User age", example = "25")
    @Min(value = 1, message = "Min age: 1")
    @Max(value = 150, message = "Max age: 150")
    private Integer age;

    @Schema(description = "User gender", example = "MALE")
    private String gender;

    @Schema(description = "User hair color", example = "Brown")
    private String hairColor;
}