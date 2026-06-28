package io.github.aryansh05.ticketing.auth.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}
