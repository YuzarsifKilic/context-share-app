package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Genre;

import java.util.List;
import java.util.Set;

public record GenreDto(
    Long id,
    String name
) {

    public static GenreDto convert(Genre from) {
        return new GenreDto(from.getId(), from.getName());
    }

    public static List<GenreDto> convertList(Set<Genre> from) {
        return from.stream().map(GenreDto::convert).toList();
    }
}
