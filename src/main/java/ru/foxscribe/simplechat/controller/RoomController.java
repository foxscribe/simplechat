package ru.foxscribe.simplechat.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.foxscribe.simplechat.dto.CreateRoomRequestDto;
import ru.foxscribe.simplechat.dto.CreateRoomResponseDto;
import ru.foxscribe.simplechat.dto.UserDto;
import ru.foxscribe.simplechat.model.Room;
import ru.foxscribe.simplechat.repository.RoomRepository;
import ru.foxscribe.simplechat.repository.UserRepository;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    private final UserRepository users;
    private final RoomRepository rooms;

    @Transactional
    @GetMapping("/get")
    public @ResponseBody List<UserDto> getUsers(
            @RequestParam("roomid") Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        var user = users.findById(userDetails.getId()).orElseThrow();
        var room = rooms.findById(roomId).orElseThrow();
        if (!user.getRooms().contains(room))
            return List.of();

        return room
                .getUsers()
                .parallelStream()
                .map(u -> new UserDto(
                        u.getId(),
                        u.getUsername()))
                .toList();
    }

    @Transactional
    @GetMapping("/join")
    public void join(
            @RequestParam("roomid") Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        var user = users.findById(userDetails.getId()).orElseThrow();
        var room = rooms.findById(roomId).orElseThrow();

        room.addUser(user);
        rooms.save(room);
    }

    @Transactional
    @PostMapping("/create")
    public @ResponseBody CreateRoomResponseDto create(
            @RequestBody CreateRoomRequestDto request) {
        var room = new Room();
        room.setName(request.getName());
        rooms.save(room);
        return new CreateRoomResponseDto(room.getId());
    }
}
