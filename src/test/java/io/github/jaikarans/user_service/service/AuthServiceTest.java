package io.github.jaikarans.user_service.service;

import io.github.jaikarans.user_service.dto.request.LoginRequest;
import io.github.jaikarans.user_service.dto.request.RegisterRequest;
import io.github.jaikarans.user_service.entity.User;
import io.github.jaikarans.user_service.exception.EmailAlreadyExistsException;
import io.github.jaikarans.user_service.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceTest for Register and login functionality")
class AuthServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    AuthService authService;

    private RegisterRequest registerRequest;
    private User user;
    private LoginRequest loginRequest;

    @BeforeEach
    void init() {
        this.registerRequest = RegisterRequest.builder()
                .email("testEmail@email.com")
                .password("testPassword")
                .fullName("test fullName")
                .build();

        this.user = new User();
        this.user.setId(123L);
        this.user.setEmail(registerRequest.email());
        this.user.setPasswordHash("hasedTestPassword");
        this.user.setFullName(registerRequest.email());

        this.loginRequest = LoginRequest.builder()
                .email(user.getEmail())
                .password("testPassword")
                .build();

    }

    @Nested
    class RegisterUserTests{

        @Test
        void registerShouldRegisterUserSuccessfully() {
            when(userRepository.existsByEmail(registerRequest.email())).thenReturn(false);
            when(passwordEncoder.encode(registerRequest.password())).thenReturn("hasedTestPassword");
            when(userRepository.save(any(User.class))).thenReturn(user);

            final User result = authService.register(registerRequest);

            assertNotNull(result);
            assertEquals(result, user);

            verify(userRepository, times(1)).existsByEmail(registerRequest.email());
            verify(passwordEncoder, times(1)).encode(registerRequest.password());
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        void registerShouldThrowEmailAlreadyExistsExceptionWhenEmailIsAlreadyExists() {
            when(userRepository.existsByEmail(registerRequest.email())).thenThrow(EmailAlreadyExistsException.class);

            assertThrows(EmailAlreadyExistsException.class, () -> authService.register((registerRequest)));
            verify(userRepository, times(1)).existsByEmail(registerRequest.email());
        }

    }

    @Nested
    class LoginUserTests{

        @Test
        void loginShouldLoginSuccessfully() {
            when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginRequest.password(), user.getPasswordHash())).thenReturn(true);

            var result = authService.login(loginRequest);

            assertNotNull(result);
            assertEquals(result, user);

            verify(userRepository, times(1)).findByEmail(loginRequest.email());
            verify(passwordEncoder, times(1)).matches(loginRequest.password(), user.getPasswordHash());

        }

        @Test
        void loginShouldThrowBadCredentialsExceptionWhenWrongEmail() {
            when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.empty());

            assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
            verify(userRepository, times(1)).findByEmail(loginRequest.email());

        }

        @Test
        void loginShouldThrowBadCredentialsExceptionWhenPasswordDoesNotMatch() {
            when(userRepository.findByEmail(loginRequest.email())).thenReturn(Optional.of(user));
            when(passwordEncoder.matches(loginRequest.password(), user.getPasswordHash())).thenReturn(false);

            assertThrows(BadCredentialsException.class,
                    () -> authService.login(loginRequest));
            verify(userRepository, times(1)).findByEmail(loginRequest.email());
            verify(passwordEncoder, times(1)).matches(loginRequest.password(), user.getPasswordHash());
        }


    }

}