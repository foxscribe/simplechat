package ru.foxscribe.simplechat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Chat rooms", description = "Endpoints for interacting with chat rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/get")
    @Operation(
            summary = "Get members of the selected room",
            description = "Returns a list of users in the selected chat room."
    )
    @ApiResponse(responseCode = "200", description = "Operation successful")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "403", description = "Insufficient permissions")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public List<UserDto> getUsers(
            @Parameter(description = "ID of the chat room", required = true)
            @RequestParam("roomid")
            Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return roomService.getUsers(roomId, userDetails.getId());
    }

    @GetMapping("/join")
    @Operation(
            summary = "Join a chat room",
            description = "Adds user into a chat room."
    )
    @ApiResponse(responseCode = "200", description = "Operation successful")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public void join(
            @Parameter(description = "ID of the chat room", required = true)
            @RequestParam("roomid")
            Long roomId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        roomService.join(roomId, userDetails.getId());
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a chat room",
            description = "Creates an empty chat room. Doesn't add user to it automatically."
    )
    @ApiResponse(responseCode = "200", description = "Operation successful")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public CreateRoomResponseDto create(
            @RequestBody CreateRoomRequestDto request) {
        return roomService.create(request.getName());
    }
}