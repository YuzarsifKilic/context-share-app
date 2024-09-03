package com.yuzarsif.contextshare.userservice.dto;

import com.yuzarsif.contextshare.userservice.model.Purpose;

public record EnableUserRequest(
    String email,
    Integer code,
    Purpose purpose
) {
}
