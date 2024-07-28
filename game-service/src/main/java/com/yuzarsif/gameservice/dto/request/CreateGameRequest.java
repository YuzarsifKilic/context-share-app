package com.yuzarsif.gameservice.dto.request;

import java.util.List;

public record CreateGameRequest(
        String name,
        String description,
        String releaseDate,
        String mainImage,
        List<Long> developers,
        List<Long> publishers,
        List<Long> genres,
        List<Long> features,
        List<Long> platforms,
        List<Long> audioLanguages,
        List<Long> subtitleLanguages,
        Long systemRequirements
) {
}
