package io.github.jaikarans.user_service.dto.request;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String email,
        String password,
        String fullName
) {}
