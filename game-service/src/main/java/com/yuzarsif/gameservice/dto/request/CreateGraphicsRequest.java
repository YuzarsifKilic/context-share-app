package com.yuzarsif.gameservice.dto.request;

public record CreateGraphicsRequest(
    String brand,
    String version,
    String description
) {
}
