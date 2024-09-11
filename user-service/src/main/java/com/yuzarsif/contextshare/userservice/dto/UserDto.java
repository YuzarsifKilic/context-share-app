package com.yuzarsif.contextshare.userservice.dto;

import com.yuzarsif.contextshare.userservice.model.Role;
import com.yuzarsif.contextshare.userservice.model.User;

import java.util.List;

public record UserDto(
        String id,
        String firstName,
        String lastName,
        String phone,
        String email,
        Role role,
        Boolean enable
) {

    public static UserDto convert(User from) {
        return new UserDto(
                from.getId(),
                from.getFirstName(),
                from.getLastName(),
                from.getPhone(),
                from.getEmail(),
                from.getRole(),
                from.getEnable());
    }

    public static List<UserDto> convert(List<User> from) {
        return from.stream().map(UserDto::convert).toList();
    }
}
