package ru.foxscribe.simplechat.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.foxscribe.simplechat.dto.RegisterRequest;
import ru.foxscribe.simplechat.model.User;
import ru.foxscribe.simplechat.repository.UserRepository;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setSecret(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }
}
