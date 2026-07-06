package ru.foxscribe.simplechat.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User registration request")
public record RegisterRequest(
        @Schema(description = "Visible username of a new user", example = "john_doe")
        String username,
        @Schema(description = "Password", example = "secure password 123")
        String password
) {
}
