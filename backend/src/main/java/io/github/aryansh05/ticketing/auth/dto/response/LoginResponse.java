package io.github.aryansh05.ticketing.auth.dto.response;

import io.github.aryansh05.ticketing.user.dto.response.UserResponse;

public record LoginResponse(
        String accessToken,
        UserResponse user
) {
}
