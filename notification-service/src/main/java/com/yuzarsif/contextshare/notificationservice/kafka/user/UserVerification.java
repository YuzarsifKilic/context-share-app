package com.yuzarsif.contextshare.notificationservice.kafka.user;

public record UserVerification(
        String email,
        String firstName,
        String lastName,
        Integer code
) {
}

