package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRoomResponseDto {
    @JsonProperty("id")
    private Long id;
}
