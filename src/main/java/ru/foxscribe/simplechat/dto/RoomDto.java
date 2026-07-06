package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Representation of a chat room")
public class RoomDto {
    @JsonProperty("id")
    @Schema(description = "Unique identifier of the room", example = "13")
    private Long id;

    @JsonProperty("name")
    @Schema(description = "Visible name of the chat room", example = "Simple Chat")
    private String name;
}
