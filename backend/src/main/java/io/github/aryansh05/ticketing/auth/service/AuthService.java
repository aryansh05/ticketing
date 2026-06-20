package io.github.aryansh05.ticketing.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.aryansh05.ticketing.auth.dto.request.RegisterRequest;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import io.github.aryansh05.ticketing.auth.entity.User;
import io.github.aryansh05.ticketing.shared.exceptions.EmailAlreadyExistsException;
import io.github.aryansh05.ticketing.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApiSuccessResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) throw new EmailAlreadyExistsException();
        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .build();
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return new ApiSuccessResponse(
                true,
                "User registered successfully"
        );
    }
}
