package com.yuzarsif.seriesservice.dto.request;

public record CreateSeasonRequest(
        Integer seasonNumber,
        String url,
        Integer episodeCount,
        Boolean mostPopular,
        String storyLine
) {
}
