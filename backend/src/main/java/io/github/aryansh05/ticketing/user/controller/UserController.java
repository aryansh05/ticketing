package io.github.aryansh05.ticketing.user.controller;

import io.github.aryansh05.ticketing.auth.security.UserPrincipal;
import io.github.aryansh05.ticketing.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UserPrincipal req) {
        UserResponse response = new UserResponse(
                req.id(),
                req.fullName(),
                req.email(),
                req.authProvider()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
