package ru.foxscribe.simplechat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.foxscribe.simplechat.dto.RoomDto;
import ru.foxscribe.simplechat.dto.UserDto;
import ru.foxscribe.simplechat.service.UserService;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for retrieving information about current user")
public class UserController {
    private final UserService userService;

    @GetMapping("/myrooms")
    @Operation(
            summary = "Get current user's rooms",
            description = "Returns a list of rooms the current user is member of."
    )
    @ApiResponse(responseCode = "200", description = "Operation successful")
    public Set<RoomDto> getRooms(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getMyRooms(userDetails.getId());
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get current user's info",
            description = "Returns details of the currently authenticated user."
    )
    @ApiResponse(responseCode = "200", description = "Operation successful")
    public UserDto me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getMe(userDetails.getId());
    }
}