package io.github.aryansh05.ticketing.auth.controller;

import io.github.aryansh05.ticketing.auth.dto.response.UserResponse;
import io.github.aryansh05.ticketing.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal UUID id) {
        UserResponse response = userService.me(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
