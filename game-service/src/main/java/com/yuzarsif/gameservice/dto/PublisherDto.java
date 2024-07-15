package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Publisher;

public record PublisherDto(
    Long id,
    String name
) {

    public static PublisherDto convert(Publisher from) {
        return new PublisherDto(from.getId(), from.getName());
    }
}
