package io.github.jaikarans.user_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlaceOrderResponse(
        @JsonProperty("orderId")
        Long orderId,
        String orderItem,
        String message

) {}
