package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Response to request to create a new chat room")
public class CreateRoomResponseDto {
    @JsonProperty("id")
    @Schema(description = "Unique identifier of the new chat room", example = "13")
    private Long id;
}
