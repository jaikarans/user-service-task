package io.github.jaikarans.user_service.dto.request;

public record LoginRequest(
        String email,
        String password
) {}