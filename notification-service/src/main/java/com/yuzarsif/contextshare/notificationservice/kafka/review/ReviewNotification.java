package com.yuzarsif.contextshare.notificationservice.kafka.review;

public record ReviewNotification(
        String email,
        String firstName,
        String lastName,
        String contextName,
        String review
) {
}
