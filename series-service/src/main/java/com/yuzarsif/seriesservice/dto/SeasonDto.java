package com.yuzarsif.seriesservice.dto;

import com.yuzarsif.seriesservice.model.Season;

import java.util.List;
import java.util.Set;

public record SeasonDto(
    Long id,
    Integer seasonNumber,
    String url,
    Integer episodeCount,
    Boolean mostPopular,
    String storyLine,
    List<EpisodeDto> episodes
) {

    public static SeasonDto convert(Season from) {
        return new SeasonDto(
            from.getId(),
            from.getSeasonNumber(),
            from.getUrl(),
            from.getEpisodeCount(),
            from.getMostPopular(),
            from.getStoryLine(),
            EpisodeDto.convert(from.getEpisodes()));
    }

    public static List<SeasonDto> convert(Set<Season> from) {
        return from.stream().map(SeasonDto::convert).toList();
    }
}
