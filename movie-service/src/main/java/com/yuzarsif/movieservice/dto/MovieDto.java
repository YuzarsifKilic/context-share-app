package com.yuzarsif.movieservice.dto;

import com.yuzarsif.movieservice.model.Movie;
import lombok.Builder;

import java.util.List;

@Builder
public record MovieDto(
    Long id,
    String name,
    String storyLine,
    String imageUrl,
    String releaseDate,
    List<MovieCategoryDto> categories,
    List<DirectorDto> directors,
    List<StarDto> stars,
    List<WriterDto> writers
) {

    public static MovieDto convert(Movie from) {
        return new MovieDto(
            from.getId(),
            from.getName(),
            from.getStoryLine(),
            from.getImageUrl(),
            from.getReleaseDate().toString(),
            MovieCategoryDto.convert(from.getCategories()),
            DirectorDto.convert(from.getDirectors()),
            StarDto.convert(from.getStars()),
            WriterDto.convert(from.getWriters()));
    }
}
