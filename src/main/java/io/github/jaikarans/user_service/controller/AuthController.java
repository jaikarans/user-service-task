package io.github.jaikarans.user_service.controller;

import io.github.jaikarans.user_service.dto.request.LoginRequest;
import io.github.jaikarans.user_service.dto.request.RegisterRequest;
import io.github.jaikarans.user_service.dto.response.LoginResponse;
import io.github.jaikarans.user_service.dto.response.RegisterResponse;
import io.github.jaikarans.user_service.security.JwtService;
import io.github.jaikarans.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        var user = authService.register(request);
        var token = jwtService.generateToken(user.getEmail(), user.getId());

        var response =  new RegisterResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                token
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var user = authService.login(request);
        var token = jwtService.generateToken(user.getEmail(), user.getId());

        var response = new LoginResponse(user.getId(), token);

        return ResponseEntity.ok(response);
    }

}
