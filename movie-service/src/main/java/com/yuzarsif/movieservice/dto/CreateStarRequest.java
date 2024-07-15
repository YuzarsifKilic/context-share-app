package com.yuzarsif.movieservice.dto;

public record CreateStarRequest(
    String name,
    String birthDate,
    String bio,
    String imageUrl,
    Float height,
    String bornPlace
) {
}
