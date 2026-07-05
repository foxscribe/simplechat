package ru.foxscribe.simplechat.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.foxscribe.simplechat.dto.LoginRequest;
import ru.foxscribe.simplechat.dto.RegisterRequest;
import ru.foxscribe.simplechat.service.AuthService;
import ru.foxscribe.simplechat.util.CustomUserDetails;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request,
                                        HttpServletRequest httpRequest) {
        authService.login(request, httpRequest);
        return ResponseEntity.ok("");
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok("");
    }
}