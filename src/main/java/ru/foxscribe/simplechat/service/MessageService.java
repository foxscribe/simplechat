package ru.foxscribe.simplechat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.foxscribe.simplechat.dto.MessageDto;
import ru.foxscribe.simplechat.repository.MessageRepository;
import ru.foxscribe.simplechat.repository.RoomRepository;
import ru.foxscribe.simplechat.repository.UserRepository;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messages;
    private final RoomRepository rooms;
    private final UserRepository users;

    @Transactional(readOnly = true)
    public List<MessageDto> get(Long roomId, Long time, Long userId) {
        var user = users.findById(userId).orElseThrow();
        var room = rooms.findById(roomId).orElseThrow();

        if (!user.getRooms().contains(room)) {
            return List.of();
        }

        var messageList = messages.retrieve(roomId, time);
        return messageList.stream()
                .map(m -> new MessageDto(
                        m.getId(),
                        m.getSender().getId(),
                        m.getSender().getUsername(),
                        m.getText(),
                        m.getTime()))
                .toList();
    }

    @Transactional
    public void create(Long roomId, String text, Long userId) {
        var message = new ru.foxscribe.simplechat.model.Message();
        message.setRoom(rooms.findById(roomId).orElseThrow());
        message.setText(text);
        message.setTime(Instant.now().getEpochSecond());
        message.setSender(users.findById(userId).orElseThrow());
        messages.save(message);
    }
}