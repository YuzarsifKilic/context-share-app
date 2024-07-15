package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Os;

import java.util.List;
import java.util.Set;

public record OsDto(
        Long id,
        String name
) {

    public static OsDto convert(Os from) {
        return new OsDto(from.getId(), from.getName());
    }

    public static List<OsDto> convertList(Set<Os> from) {
        return from.stream().map(OsDto::convert).toList();
    }
}
