package ru.foxscribe.simplechat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.foxscribe.simplechat.dto.RoomDto;
import ru.foxscribe.simplechat.dto.UserDto;
import ru.foxscribe.simplechat.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository users;

    public Set<RoomDto> getMyRooms(Long userId) {
        var user = users.findById(userId).orElseThrow();
        return user.getRooms().stream()
                .map(r -> new RoomDto(r.getId(), r.getName()))
                .collect(Collectors.toSet());
    }

    public UserDto getMe(Long userId) {
        var user = users.findById(userId).orElseThrow();
        return new UserDto(user.getId(), user.getUsername());
    }
}