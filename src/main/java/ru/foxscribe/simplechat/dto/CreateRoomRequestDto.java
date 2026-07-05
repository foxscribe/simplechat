package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomRequestDto {
    @JsonProperty
    private String name;
}
