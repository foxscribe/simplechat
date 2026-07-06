package ru.foxscribe.simplechat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User login request")
public record LoginRequest(
        @Schema(description = "Visible username of the user", example = "john_doe")
        String username,
        @Schema(description = "Password", example = "secure password 123")
        String password) {
}
