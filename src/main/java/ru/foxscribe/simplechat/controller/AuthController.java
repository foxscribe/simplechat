package ru.foxscribe.simplechat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Authentication",
        description = "Endpoints for registration, login, and session management"
)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user. Doesn't create session automatically."
    )
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @ApiResponse(responseCode = "409", description = "Username already taken")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("");
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login user and create a session.",
            description = "The JSESSIONID cookie will be set in the response."
    )
    public ResponseEntity<String> login(@RequestBody LoginRequest request,
                                        HttpServletRequest httpRequest) {
        authService.login(request, httpRequest);
        return ResponseEntity.ok("");
    }

    @GetMapping("/me")
    @Operation(summary = "Check session validity")
    public ResponseEntity<String> me(
            @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok("");
    }
}