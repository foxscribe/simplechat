package ru.foxscribe.simplechat.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.foxscribe.simplechat.dto.CreateRoomRequestDto;
import ru.foxscribe.simplechat.dto.CreateRoomResponseDto;
import ru.foxscribe.simplechat.dto.UserDto;
import ru.foxscribe.simplechat.service.RoomService;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/get")
    public List<UserDto> getUsers(
            @RequestParam("roomid") Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return roomService.getUsers(roomId, userDetails.getId());
    }

    @GetMapping("/join")
    public void join(
            @RequestParam("roomid") Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        roomService.join(roomId, userDetails.getId());
    }

    @PostMapping("/create")
    public CreateRoomResponseDto create(
            @RequestBody CreateRoomRequestDto request) {
        return roomService.create(request.getName());
    }
}