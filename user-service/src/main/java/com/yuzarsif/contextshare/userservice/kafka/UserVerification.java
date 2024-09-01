package com.yuzarsif.contextshare.userservice.kafka;

public record UserVerification(
    String email,
    Integer code
) {
}
