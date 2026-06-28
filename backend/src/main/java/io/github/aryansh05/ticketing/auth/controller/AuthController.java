package io.github.aryansh05.ticketing.auth.controller;

import io.github.aryansh05.ticketing.auth.dto.request.LoginRequest;
import io.github.aryansh05.ticketing.auth.dto.request.RefreshTokenRequest;
import io.github.aryansh05.ticketing.auth.dto.response.LoginResponse;
import io.github.aryansh05.ticketing.auth.dto.response.LoginResults;
import io.github.aryansh05.ticketing.auth.dto.response.RefreshTokenResponse;
import io.github.aryansh05.ticketing.auth.dto.response.RefreshTokenResults;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.aryansh05.ticketing.auth.dto.request.RegisterRequest;
import io.github.aryansh05.ticketing.shared.dto.response.ApiSuccessResponse;
import io.github.aryansh05.ticketing.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${security.jwt.refresh-ttl-seconds}")
    private long refreshTtlSeconds;
    @Value("${security.jwt.refresh-token-cookie-name}")
    private String refreshTokenCookieName;
    @Value("${security.jwt.cookie-secure}")
    private boolean secure;
    @Value("${security.jwt.cookie-http-only}")
    private boolean httpOnly;
    @Value("${security.jwt.cookie-same-site}")
    private String sameSite;
    @Value("${security.jwt.cookie-domain}")
    private String domain;

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse> register(@Valid @RequestBody RegisterRequest request) {
        ApiSuccessResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResults> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        ResponseCookie responseCookie = ResponseCookie.from(refreshTokenCookieName, response.refreshToken())
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .domain(domain)
                .maxAge(refreshTtlSeconds)
                .path("/")
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(new LoginResults(
                response.accessToken(),
                response.user()
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResults> refresh(HttpServletRequest req) {
        Cookie cookie = WebUtils.getCookie(req, refreshTokenCookieName);
        if(cookie == null) throw new BadCredentialsException("");
        RefreshTokenRequest request = new RefreshTokenRequest(cookie.getValue());
        RefreshTokenResponse response = authService.refresh(request);
        ResponseCookie responseCookie = ResponseCookie.from(refreshTokenCookieName, response.refreshToken())
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .domain(domain)
                .maxAge(refreshTtlSeconds)
                .path("/")
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(new RefreshTokenResults(
                response.accessToken()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiSuccessResponse> logout(HttpServletRequest req) {
        Cookie cookie = WebUtils.getCookie(req, refreshTokenCookieName);
        if(cookie == null) throw new BadCredentialsException("");
        RefreshTokenRequest request = new RefreshTokenRequest(cookie.getValue());
        ApiSuccessResponse response = authService.logout(request);
        ResponseCookie responseCookie = ResponseCookie.from(refreshTokenCookieName, "")
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .domain(domain)
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(response);
    }
}
