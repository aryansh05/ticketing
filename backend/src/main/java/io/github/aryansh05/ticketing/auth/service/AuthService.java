package io.github.aryansh05.ticketing.auth.service;

import io.github.aryansh05.ticketing.auth.domain.entity.RefreshToken;
import io.github.aryansh05.ticketing.auth.domain.repository.RefreshTokenRepository;
import io.github.aryansh05.ticketing.auth.dto.request.LoginRequest;
import io.github.aryansh05.ticketing.auth.dto.request.RefreshTokenRequest;
import io.github.aryansh05.ticketing.auth.dto.response.LoginResponse;
import io.github.aryansh05.ticketing.auth.dto.response.RefreshTokenResponse;
import io.github.aryansh05.ticketing.shared.exception.ResourceNotFoundException;
import io.github.aryansh05.ticketing.user.dto.response.UserResponse;
import io.github.aryansh05.ticketing.auth.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.refresh-ttl-seconds}")
    private long refreshTtlSeconds;

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
                "Registered successfully"
        );
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = (User) authentication.getPrincipal();
        UUID id  = user.getId();
        String userId = id.toString();

        refreshTokenRepository.deleteByUserId(id);
        refreshTokenRepository.flush();

        RefreshToken refreshTokenObj = RefreshToken.builder()
                .userId(id)
                .expiresAt(Instant.now().plusSeconds(refreshTtlSeconds))
                .build();

        String accessToken = jwtUtil.generateAccessToken(userId);
        String refreshToken = jwtUtil.generateRefreshToken(refreshTokenObj.getId().toString(), userId);

        refreshTokenRepository.save(refreshTokenObj);

        UserResponse userResponse = new UserResponse(
                userId,
                user.getFullName(),
                user.getEmail(),
                user.getAuthProvider().name()
        );
        return new LoginResponse(
                accessToken,
                refreshToken,
                userResponse
        );
    }

    @Transactional
    public RefreshTokenResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        Claims claim;
        try {
           claim = jwtUtil.extractClaims(refreshToken);
        } catch (JwtException e) {
            throw new BadCredentialsException("");
        }
        String userId = claim.getSubject();
        String jti = claim.getId();
        UUID id = UUID.fromString(jti);

        if (!"refresh".equals(claim.get("type"))) throw new BadCredentialsException("");

        Instant exp = claim.getExpiration().toInstant();
        if(exp.isBefore(Instant.now())){
            throw new BadCredentialsException("");
        }

        RefreshToken refreshTokenObj = refreshTokenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh Token not found"));

        if (!userId.equals(refreshTokenObj.getUserId().toString())) throw new BadCredentialsException("");

        refreshTokenRepository.delete(refreshTokenObj);
        refreshTokenRepository.flush();

        RefreshToken newRefreshTokenObj = RefreshToken.builder()
                .userId(UUID.fromString(userId))
                .expiresAt(Instant.now().plusSeconds(refreshTtlSeconds))
                .build();

        String newAccessToken = jwtUtil.generateAccessToken(userId);
        String newRefreshToken = jwtUtil.generateRefreshToken(newRefreshTokenObj.getId().toString(), userId);

        refreshTokenRepository.save(newRefreshTokenObj);
        return new RefreshTokenResponse(
                newAccessToken,
                newRefreshToken
        );
    }

    public ApiSuccessResponse logout(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        Claims claim;
        try {
            claim = jwtUtil.extractClaims(refreshToken);
        }catch (ExpiredJwtException e) {
            claim = e.getClaims();
        }catch (JwtException e) {
            throw new BadCredentialsException("");
        }
        String id = claim.getId();
        UUID jti = UUID.fromString(id);
        refreshTokenRepository.deleteById(jti);

        return new ApiSuccessResponse(
                true,
                "Logout successfully"
        );
    }
}
