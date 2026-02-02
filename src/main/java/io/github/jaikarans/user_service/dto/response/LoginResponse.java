package io.github.jaikarans.user_service.dto.response;

public record LoginResponse(
        Long userId,
        String accessToken
) {}
