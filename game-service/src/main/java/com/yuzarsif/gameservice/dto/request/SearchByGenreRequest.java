package com.yuzarsif.gameservice.dto.request;

import com.yuzarsif.gameservice.model.Genre;

import java.util.Set;

public record SearchByGenreRequest(
        Set<Genre> genres,
        Integer page,
        Integer size
) {
}
