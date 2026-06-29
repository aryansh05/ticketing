package io.github.aryansh05.ticketing.auth.security;

import io.github.aryansh05.ticketing.shared.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {
    public UserPrincipal getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || auth.getPrincipal() == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return (UserPrincipal) auth.getPrincipal();
    }
}
