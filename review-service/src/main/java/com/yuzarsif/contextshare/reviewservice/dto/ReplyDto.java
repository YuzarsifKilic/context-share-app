package com.yuzarsif.contextshare.reviewservice.dto;

import com.yuzarsif.contextshare.reviewservice.clients.user.UserResponse;
import com.yuzarsif.contextshare.reviewservice.model.Reply;

public record ReplyDto(
    String id,
    UserResponse user,
    String comment
) {

    public static ReplyDto convert(Reply from, UserResponse user) {
        return new ReplyDto(
            from.getId(),
            user,
            from.getComment());
    }
}
