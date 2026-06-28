package io.github.aryansh05.ticketing.auth.security;

import io.github.aryansh05.ticketing.user.domain.entity.AuthProvider;
import java.util.UUID;

public record UserPrincipal(
        UUID id,
        String fullName,
        String email,
        AuthProvider authProvider
) {
}
