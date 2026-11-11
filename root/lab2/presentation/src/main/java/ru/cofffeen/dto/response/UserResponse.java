package ru.cofffeen.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User information response")
public class UserResponse {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User login", example = "john_doe")
    private String login;

    @Schema(description = "User full name", example = "John Doe")
    private String name;

    @Schema(description = "User age", example = "25")
    private Integer age;

    @Schema(description = "User gender", example = "MALE")
    private String gender;

    @Schema(description = "User hair color", example = "Brown")
    private String hairColor;

    @Schema(description = "List of friend IDs")
    private List<Long> friendIds;

    @Schema(description = "List of account IDs")
    private List<Long> accountIds;
}