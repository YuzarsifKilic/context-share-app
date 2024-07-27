package com.yuzarsif.seriesservice.dto.request;

public record CreateDirectorRequest(
    String name,
    String birthDate,
    String bio,
    String imageUrl
) {
}
