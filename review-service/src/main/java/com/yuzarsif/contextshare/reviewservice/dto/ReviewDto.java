package com.yuzarsif.contextshare.reviewservice.dto;

import com.yuzarsif.contextshare.reviewservice.clients.user.UserResponse;
import com.yuzarsif.contextshare.reviewservice.model.ContextType;
import com.yuzarsif.contextshare.reviewservice.model.Review;

public record ReviewDto(
        String id,
        ContextType contextType,
        Long contextId,
        String comment,
        Float rating,
        UserResponse user
) {
    public static ReviewDto convert(Review from, UserResponse user) {
        return new ReviewDto(
                from.getId(),
                from.getContextType(),
                from.getContextId(),
                from.getComment(),
                from.getRating(),
                user);
    }
}
