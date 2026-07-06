package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Representation of a chat message")
public class MessageDto {
    @Schema(description = "Unique identifier of the message", example = "101")
    private Long id;

    @JsonProperty("sender_id")
    @Schema(description = "ID of the user who sent the message", example = "5")
    private Long senderId;

    @JsonProperty("sender")
    @Schema(description = "Username of the sender", example = "john_doe")
    private String senderName;

    @Schema(description = "The text content of the message", example = "Hello everyone!")
    private String text;

    @Schema(description = "Epoch time in seconds when the message was sent", example = "1720250400")
    private Long time;
}
