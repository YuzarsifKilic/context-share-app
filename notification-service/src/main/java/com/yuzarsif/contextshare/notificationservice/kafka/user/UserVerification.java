package com.yuzarsif.contextshare.notificationservice.kafka.user;

public record UserVerification(
        String email,
        Integer code
) {
}

