package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Representation of a user")
public class UserDto {
    @JsonProperty("id")
    @Schema(description = "Unique identifier of the user", example = "5")
    private Long id;

    @JsonProperty("username")
    @Schema(description = "Visible username of the user", example = "john_doe")
    private String username;
}
