package com.yuzarsif.contextshare.reviewservice.clients.user;

public record UserResponse(
        String id,
        String firstName,
        String lastName,
        String phone,
        String email,
        Role role,
        Boolean enable
) {

}
