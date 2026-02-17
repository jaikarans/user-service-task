package io.github.jaikarans.user_service.dto.request;

import lombok.Builder;

@Builder
public record LoginRequest(
        String email,
        String password
) {}