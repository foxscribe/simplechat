package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateMessageRequestDto {
    @JsonProperty("room")
    private Long roomId;

    private String text;
}
