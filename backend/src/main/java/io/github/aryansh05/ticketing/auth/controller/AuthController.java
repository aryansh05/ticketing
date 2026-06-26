package io.github.aryansh05.ticketing.auth.controller;

import io.github.aryansh05.ticketing.auth.dto.request.LoginRequest;
import io.github.aryansh05.ticketing.auth.dto.request.RefreshTokenRequest;
import io.github.aryansh05.ticketing.auth.dto.response.LoginResponse;
import io.github.aryansh05.ticketing.auth.dto.response.RefreshTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.aryansh05.ticketing.auth.dto.request.RegisterRequest;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import io.github.aryansh05.ticketing.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse> register(@Valid @RequestBody RegisterRequest request) {
        ApiSuccessResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse response = authService.refresh(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse> logout(@Valid @RequestBody RefreshTokenRequest request) {
        ApiSuccessResponse response = authService.logout(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
