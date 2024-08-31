package com.yuzarsif.contextshare.userservice.dto;

import com.yuzarsif.contextshare.userservice.model.Role;

public record CreateUserRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        Role role
) {
}
