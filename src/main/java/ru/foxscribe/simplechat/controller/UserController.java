package ru.foxscribe.simplechat.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.foxscribe.simplechat.dto.RoomDto;
import ru.foxscribe.simplechat.dto.UserDto;
import ru.foxscribe.simplechat.repository.UserRepository;
import ru.foxscribe.simplechat.util.CustomUserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository users;

    @Transactional
    @GetMapping("/myrooms")
    public @ResponseBody Set<RoomDto> getRooms(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        var user = users.findById(userDetails.getId()).orElseThrow();
        return user
                .getRooms()
                .parallelStream()
                .map(r -> new RoomDto(
                    r.getId(),
                    r.getName()
                ))
                .collect(Collectors.toSet());
    }

    @GetMapping("/me")
    public @ResponseBody UserDto me(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        var user = users.findById(userDetails.getId()).orElseThrow();
        return new UserDto(user.getId(), user.getUsername());
    }
}
