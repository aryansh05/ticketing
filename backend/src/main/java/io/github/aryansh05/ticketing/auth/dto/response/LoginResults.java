package io.github.aryansh05.ticketing.auth.dto.response;

import io.github.aryansh05.ticketing.user.dto.response.UserResponse;

public record LoginResults(
        String accessToken,
        UserResponse user
) {
}
