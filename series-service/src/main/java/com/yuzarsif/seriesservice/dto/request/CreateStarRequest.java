package com.yuzarsif.seriesservice.dto.request;

public record CreateStarRequest(
    String name,
    String birthDate,
    String bio,
    String imageUrl,
    Float height,
    String bornPlace
) {
}
