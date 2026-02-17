package io.github.jaikarans.user_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlaceOrderRequest(
        String email,
        @JsonProperty("userId")
        Long userId,
        String orderItem
) {}
