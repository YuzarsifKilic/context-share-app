package com.yuzarsif.seriesservice.dto;

import com.yuzarsif.seriesservice.model.Star;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record StarDto(
        Long id,
        String name,
        String birthDate,
        String bio,
        String imageUrl,
        Float height,
        String bornPlace
) {

    public static StarDto convert(Star from) {
        return new StarDto(
                from.getId(),
                from.getName(),
                from.getBirthDate().toString(),
                from.getBio(),
                from.getImageUrl(),
                from.getHeight(),
                from.getBornPlace());
    }

    public static List<StarDto> convert(Set<Star> from) {
        return from.stream().map(StarDto::convert).toList();
    }
}
