package com.yuzarsif.movieservice.dto;

public record CreateWriterRequest(
        String name,
        String birthDate,
        String bio,
        String imageUrl,
        Float height,
        String bornPlace
) {
}
