package ru.foxscribe.simplechat.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.foxscribe.simplechat.dto.CreateMessageRequestDto;
import ru.foxscribe.simplechat.dto.MessageDto;
import ru.foxscribe.simplechat.service.MessageService;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/get")
    public List<MessageDto> get(
            @RequestParam("room") Long roomId,
            @RequestParam("since") Long time,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return messageService.get(roomId, time, userDetails.getId());
    }

    @PostMapping("/create")
    public void create(
            @RequestBody CreateMessageRequestDto request,
            @AuthenticationPrincipal CustomUserDetails user) {
        messageService.create(request.getRoomId(), request.getText(),
                user.getId());
    }
}