package ru.foxscribe.simplechat.controller;

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
public class UserController {
    private final UserService userService;

    @GetMapping("/myrooms")
    public Set<RoomDto> getRooms(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getMyRooms(userDetails.getId());
    }

    @GetMapping("/me")
    public UserDto me(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getMe(userDetails.getId());
    }
}