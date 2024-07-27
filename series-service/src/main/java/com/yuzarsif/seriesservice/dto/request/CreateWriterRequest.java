package com.yuzarsif.seriesservice.dto.request;

public record CreateWriterRequest(
    String name,
    String birthDate,
    String bio,
    String imageUrl,
    Float height,
    String bornPlace
) {
}
