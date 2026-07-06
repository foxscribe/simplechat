package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Request to send a new message")
public class CreateMessageRequestDto {
    @JsonProperty("room")
    @Schema(description = "Identifier of the destination chat room", example = "13")
    private Long roomId;

    @Schema(description = "Contents of a new message", example = "Hello!")
    private String text;
}
