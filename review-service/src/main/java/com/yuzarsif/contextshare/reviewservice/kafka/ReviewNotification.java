package com.yuzarsif.contextshare.reviewservice.kafka;

public record ReviewNotification(
        String email,
        String firstName,
        String lastName,
        String contextName,
        String review
) {
}
