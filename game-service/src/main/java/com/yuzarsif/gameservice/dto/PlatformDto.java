package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Platform;

import java.util.List;
import java.util.Set;

public record PlatformDto(
    Long id,
    String name
) {

    public static PlatformDto convert(Platform from) {
        return new PlatformDto(from.getId(), from.getName());
    }

    public static List<PlatformDto> convertList(Set<Platform> from) {
        return from.stream().map(PlatformDto::convert).toList();
    }
}
