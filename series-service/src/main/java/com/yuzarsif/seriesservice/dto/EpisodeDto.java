package com.yuzarsif.seriesservice.dto;

import com.yuzarsif.seriesservice.model.Episode;

import java.util.List;
import java.util.Set;

public record EpisodeDto(
    Long id,
    Integer episodeNumber,
    String name,
    String url,
    String episodeDate,
    Boolean mostPopular,
    String storyLine
) {

    public static EpisodeDto convert(Episode from) {
        return new EpisodeDto(
            from.getId(),
            from.getEpisodeNumber(),
            from.getName(),
            from.getUrl(),
            from.getEpisodeDate().toString(),
            from.getMostPopular(),
            from.getStoryLine());
    }

    public static List<EpisodeDto> convert(Set<Episode> from) {
        return from.stream().map(EpisodeDto::convert).toList();
    }
}
