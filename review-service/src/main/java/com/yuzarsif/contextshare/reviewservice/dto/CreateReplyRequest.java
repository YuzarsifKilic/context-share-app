package com.yuzarsif.contextshare.reviewservice.dto;

public record CreateReplyRequest(
        String userId,
        String comment
) {
}
