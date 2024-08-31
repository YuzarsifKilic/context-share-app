package com.yuzarsif.contextshare.userservice.service;

import com.yuzarsif.contextshare.userservice.dto.LoginRequest;
import com.yuzarsif.contextshare.userservice.dto.LoginResponse;
import com.yuzarsif.contextshare.userservice.model.User;
import com.yuzarsif.contextshare.userservice.repository.UserRepository;
import com.yuzarsif.contextshare.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(request.email());
            User user = userRepository.findByEmail(request.email()).get();
            return new LoginResponse(token, user.getRole());
        } else {
            throw new RuntimeException("invalid credentials");
        }
    }
}
