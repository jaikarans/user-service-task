package io.github.jaikarans.user_service.controller;

import io.github.jaikarans.user_service.dto.request.OrderRequest;
import io.github.jaikarans.user_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<?> login(@RequestBody OrderRequest request, Authentication auth) {
        var val = orderService.putOrder(request);
        return ResponseEntity.ok("hi from order");
    }
}
