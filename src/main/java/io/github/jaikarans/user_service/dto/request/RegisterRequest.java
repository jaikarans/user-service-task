package io.github.jaikarans.user_service.dto.request;

public record RegisterRequest(
        String email,
        String password,
        String fullName
) {}
