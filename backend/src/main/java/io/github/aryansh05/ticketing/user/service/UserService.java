package io.github.aryansh05.ticketing.user.service;

import io.github.aryansh05.ticketing.auth.security.AuthenticatedUser;
import io.github.aryansh05.ticketing.auth.security.UserPrincipal;
import io.github.aryansh05.ticketing.user.domain.repository.UserRepository;
import io.github.aryansh05.ticketing.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticatedUser authenticatedUser;

    public UserResponse getMe(){
        UserPrincipal user = authenticatedUser.getAuthenticatedUser();
        return new UserResponse(
                user.id(),
                user.fullName(),
                user.email(),
                user.authProvider()
        );
    }
}
