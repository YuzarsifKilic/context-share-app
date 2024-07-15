package com.yuzarsif.movieservice.dto;

public record CreateDirectorRequest(
    String name,
    String birthDate,
    String bio,
    String imageUrl
) {
}
