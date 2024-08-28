package com.yuzarsif.gameservice.dto.request;

public record CreateProcessorRequest(
    String brand,
    String version,
    String description
) {
}
