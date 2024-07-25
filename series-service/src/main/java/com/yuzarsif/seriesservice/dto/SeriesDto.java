package com.yuzarsif.seriesservice.dto;

import com.yuzarsif.seriesservice.model.Season;
import com.yuzarsif.seriesservice.model.Series;

import java.util.List;

public record SeriesDto(
    Long id,
    String name,
    String imageUrl,
    String releaseDate,
    Boolean isFinished,
    Boolean mostPopular,
    Integer seasonCount,
    Integer episodeCount,
    List<WriterDto> writers,
    List<DirectorDto> directors,
    List<StarDto> stars,
    List<SeasonDto> seasons
) {

    public static SeriesDto convert(Series from) {
        return new SeriesDto(
            from.getId(),
            from.getName(),
            from.getImageUrl(),
            from.getReleaseDate().toString(),
            from.getIsFinished(),
            from.getMostPopular(),
            from.getSeasonCount(),
            from.getEpisodeCount(),
            WriterDto.convert(from.getWriters()),
            DirectorDto.convert(from.getDirectors()),
            StarDto.convert(from.getStars()),
            SeasonDto.convert(from.getSeasons()));
    }
}
