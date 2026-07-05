package ru.foxscribe.simplechat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.foxscribe.simplechat.dto.CreateRoomResponseDto;
import ru.foxscribe.simplechat.dto.UserDto;
import ru.foxscribe.simplechat.model.Room;
import ru.foxscribe.simplechat.repository.RoomRepository;
import ru.foxscribe.simplechat.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final UserRepository users;
    private final RoomRepository rooms;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(Long roomId, Long userId) {
        var user = users.findById(userId).orElseThrow();
        var room = rooms.findById(roomId).orElseThrow();

        if (!user.getRooms().contains(room)) {
            return List.of();
        }

        return room.getUsers().stream()
                .map(u -> new UserDto(u.getId(), u.getUsername()))
                .toList();
    }

    @Transactional
    public void join(Long roomId, Long userId) {
        var user = users.findById(userId).orElseThrow();
        var room = rooms.findById(roomId).orElseThrow();

        room.addUser(user);
        rooms.save(room);
    }

    @Transactional
    public CreateRoomResponseDto create(String name) {
        var room = new Room();
        room.setName(name);
        rooms.save(room);
        return new CreateRoomResponseDto(room.getId());
    }
}