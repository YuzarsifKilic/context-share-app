package com.yuzarsif.contextshare.userservice.dto;

public record LoginRequest(
        String email,
        String password
) {
}
