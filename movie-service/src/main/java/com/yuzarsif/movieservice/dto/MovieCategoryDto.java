package com.yuzarsif.movieservice.dto;

import com.yuzarsif.movieservice.model.MovieCategory;

import java.util.List;
import java.util.Set;

public record MovieCategoryDto(
        Long id,
        String name
) {

    public static MovieCategoryDto convert(MovieCategory from) {
        return new MovieCategoryDto(from.getId(), from.getName());
    }

    public static List<MovieCategoryDto> convert(Set<MovieCategory> from) {
        return from.stream().map(MovieCategoryDto::convert).toList();
    }
}
