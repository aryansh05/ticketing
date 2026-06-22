package io.github.aryansh05.ticketing.auth.service;

import io.github.aryansh05.ticketing.auth.dto.request.LoginRequest;
import io.github.aryansh05.ticketing.auth.dto.response.LoginResponse;
import io.github.aryansh05.ticketing.user.dto.response.UserResponse;
import io.github.aryansh05.ticketing.auth.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.aryansh05.ticketing.auth.dto.request.RegisterRequest;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import io.github.aryansh05.ticketing.user.domain.entity.User;
import io.github.aryansh05.ticketing.shared.exception.EmailAlreadyExistsException;
import io.github.aryansh05.ticketing.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = (User) authentication.getPrincipal();
        String userId = user.getId().toString();
        String accessToken = jwtUtil.generateAccessToken(userId);
        UserResponse userResponse = new UserResponse(
                userId,
                user.getFullName(),
                user.getEmail(),
                user.getAuthProvider().name()
        );
        return new LoginResponse(
                accessToken,
                userResponse
        );
    }
}
