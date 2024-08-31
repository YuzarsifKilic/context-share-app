package com.yuzarsif.contextshare.reviewservice.dto;

import com.yuzarsif.contextshare.reviewservice.model.ContextType;

public record CreateReviewRequest(
        String userId,
        ContextType contextType,
        Long contextId,
        String comment,
        Float rating
) {
}
