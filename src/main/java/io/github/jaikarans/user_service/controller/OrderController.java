package io.github.jaikarans.user_service.controller;

import io.github.jaikarans.user_service.dto.request.OrderRequest;
import io.github.jaikarans.user_service.dto.response.PlaceOrderResponse;
import io.github.jaikarans.user_service.security.JwtService;
import io.github.jaikarans.user_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final JwtService jwtService;

    @PostMapping("/order")
    public ResponseEntity<PlaceOrderResponse> login(@RequestBody OrderRequest request,
                                                    @RequestHeader("Authorization") String token) {
        System.out.println("Auth token "+ token);
        System.out.println("email: "+jwtService.extractEmailFromJwt(token.substring(7)));
        System.out.println("userUid: "+jwtService.extractUidFromJwt(token.substring(7)));
        var order = orderService.putOrder(request, token.substring(7));

        return ResponseEntity.ok(order);
    }

    @GetMapping("/hi")
    public ResponseEntity<?> hi() {
        return ResponseEntity.ok("hi from user service");
    }
}
