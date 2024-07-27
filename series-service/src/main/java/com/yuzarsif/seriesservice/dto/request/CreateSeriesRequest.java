package com.yuzarsif.seriesservice.dto.request;

import com.yuzarsif.seriesservice.model.Language;

import java.util.List;

public record CreateSeriesRequest(
    String name,
    String imageUrl,
    String releaseDate,
    Boolean isFinished,
    Boolean mostPopular,
    Integer seasonCount,
    Integer episodeCount,
    String storyLine,
    Language language,
    List<Long> directors,
    List<Long> stars,
    List<Long> writers,
    List<Long> categories
) {
}
