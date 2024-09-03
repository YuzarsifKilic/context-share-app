package com.yuzarsif.contextshare.userservice.controller;

import com.yuzarsif.contextshare.userservice.dto.*;
import com.yuzarsif.contextshare.userservice.kafka.UserVerification;
import com.yuzarsif.contextshare.userservice.service.AuthService;
import com.yuzarsif.contextshare.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/verify")
    public void verify(@RequestBody EnableUserRequest request) {
        userService.enableUser(request);
    }
}
