package io.github.aryansh05.ticketing.auth.dto.response;

public record LoginResponse(
        String accessToken,
        UserResponse user
) {
}
