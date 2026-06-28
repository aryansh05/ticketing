package io.github.aryansh05.ticketing.user.dto.response;

public record UserResponse(
        String id,
        String fullName,
        String email,
        String authProvider
) {
}
