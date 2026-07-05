package ru.foxscribe.simplechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class MessageDto {
    private Long id;

    @JsonProperty("sender_id")
    private Long senderId;

    @JsonProperty("sender")
    private String senderName;

    private String text;

    private Long time;
}
