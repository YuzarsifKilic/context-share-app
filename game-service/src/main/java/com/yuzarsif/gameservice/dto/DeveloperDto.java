package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Developer;

import java.util.List;
import java.util.Set;

public record DeveloperDto(
    Long id,
    String name
) {

    public static DeveloperDto convert(Developer from) {
        return new DeveloperDto(from.getId(), from.getName());
    }

    public static List<DeveloperDto> convertList(Set<Developer> from) {
        return from.stream().map(DeveloperDto::convert).toList();
    }
}
