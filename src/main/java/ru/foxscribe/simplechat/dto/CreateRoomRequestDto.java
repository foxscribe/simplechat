package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to create a new chat room")
public class CreateRoomRequestDto {
    @JsonProperty
    @Schema(description = "Visible name of a new chat room", example = "Simple Chat")
    private String name;
}
