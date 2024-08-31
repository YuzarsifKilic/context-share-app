package com.yuzarsif.contextshare.userservice.dto;

import com.yuzarsif.contextshare.userservice.model.Role;

public record LoginResponse(
        String token,
        Role role
) {
}
