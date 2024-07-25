package com.yuzarsif.seriesservice.dto;


import com.yuzarsif.seriesservice.model.SeriesCategory;

import java.util.List;
import java.util.Set;

public record SeriesCategoryDto(
        Long id,
        String name
) {

    public static SeriesCategoryDto convert(SeriesCategory from) {
        return new SeriesCategoryDto(from.getId(), from.getName());
    }

    public static List<SeriesCategoryDto> convert(Set<SeriesCategory> from) {
        return from.stream().map(SeriesCategoryDto::convert).toList();
    }
}
