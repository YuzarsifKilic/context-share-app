package com.yuzarsif.seriesservice.dto;

import com.yuzarsif.seriesservice.model.Director;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record DirectorDto(
    Long id,
    String name,
    String birthDate,
    String bio,
    String imageUrl
) {

    public static DirectorDto convert(Director from) {
        return new DirectorDto(
            from.getId(),
            from.getName(),
            from.getBirthDate().toString(),
            from.getBio(),
            from.getImageUrl());
    }

    public static List<DirectorDto> convert(Set<Director> from) {
        return from.stream().map(DirectorDto::convert).toList();
    }
}
