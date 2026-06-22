package io.github.aryansh05.ticketing.user.service;

import io.github.aryansh05.ticketing.user.dto.response.UserResponse;
import io.github.aryansh05.ticketing.user.domain.entity.User;
import io.github.aryansh05.ticketing.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse me(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException(""));
        return new UserResponse(
                id.toString(),
                user.getFullName(),
                user.getEmail(),
                user.getAuthProvider().name()
        );
    }
}
