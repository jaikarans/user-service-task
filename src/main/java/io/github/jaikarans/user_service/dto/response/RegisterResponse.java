package io.github.jaikarans.user_service.dto.response;

public record RegisterResponse(
        Long userId,
        String email,
        String fullName,
        String accessToken
) {}

