package ru.foxscribe.simplechat.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.foxscribe.simplechat.dto.CreateMessageRequestDto;
import ru.foxscribe.simplechat.dto.MessageDto;
import ru.foxscribe.simplechat.model.Message;
import ru.foxscribe.simplechat.repository.MessageRepository;
import ru.foxscribe.simplechat.repository.RoomRepository;
import ru.foxscribe.simplechat.repository.UserRepository;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.time.Instant;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageRepository messages;
    private final RoomRepository rooms;
    private final UserRepository users;

    @Transactional
    @GetMapping("/get")
    public @ResponseBody List<MessageDto> get(
            @RequestParam("room") Long roomId,
            @RequestParam("since") Long time,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        var user = users.findById(userDetails.getId()).orElseThrow();
        var room = rooms.findById(roomId).orElseThrow();
        if (!user.getRooms().contains(room))
            return List.of();

        var messages = this.messages.retrieve(roomId, time);
        return messages
                .parallelStream()
                .map(m -> new MessageDto(
                        m.getId(),
                        m.getSender().getId(),
                        m.getSender().getUsername(),
                        m.getText(),
                        m.getTime()))
                .toList();
    }

    @Transactional
    @PostMapping("/create")
    public void create(
            @RequestBody CreateMessageRequestDto request,
            @AuthenticationPrincipal CustomUserDetails user) {
        var message = new Message();
        message.setRoom(rooms.findById(request.getRoomId()).orElseThrow());
        message.setText(request.getText());
        message.setTime(Instant.now().getEpochSecond());
        message.setSender(users.findById(user.getId()).orElseThrow());
        messages.save(message);
    }
}
