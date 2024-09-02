package com.yuzarsif.contextshare.userservice.kafka;

public record UserVerification(
    String email,
    String firstName,
    String lastName,
    Integer code
) {
}
