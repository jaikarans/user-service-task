package io.github.jaikarans.user_service.service;

import io.github.jaikarans.user_service.dto.request.LoginRequest;
import io.github.jaikarans.user_service.dto.request.RegisterRequest;
import io.github.jaikarans.user_service.entity.User;
import io.github.jaikarans.user_service.exception.EmailAlreadyExistsException;
import io.github.jaikarans.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email()+" email already Registered");
        }

        var user = new User();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        return userRepository.save(user);
    }

    public User login(LoginRequest request) throws RuntimeException {
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("email doesn't exist"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Password doesn't match");
        }

        return user;

    }


}
