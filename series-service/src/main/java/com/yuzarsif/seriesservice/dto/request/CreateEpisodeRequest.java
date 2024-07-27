package com.yuzarsif.seriesservice.dto.request;

public record CreateEpisodeRequest(
        Integer episodeNumber,
        String name,
        String imageUrl,
        String episodeDate,
        Boolean isFinished,
        Boolean mostPopular,
        String storyLine
) {
}
