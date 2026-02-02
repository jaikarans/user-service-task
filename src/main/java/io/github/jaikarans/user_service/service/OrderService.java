package io.github.jaikarans.user_service.service;

import io.github.jaikarans.user_service.dto.request.OrderRequest;
import io.github.jaikarans.user_service.entity.User;
import io.github.jaikarans.user_service.repository.UserRepository;
import io.github.jaikarans.user_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User putOrder(OrderRequest request) throws RuntimeException {
        return new User();
    }
}
