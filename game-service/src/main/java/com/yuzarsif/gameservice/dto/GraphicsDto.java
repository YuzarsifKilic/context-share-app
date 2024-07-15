package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Graphics;

import java.util.List;
import java.util.Set;

public record GraphicsDto(
    Long id,
    String brand,
    String version
) {

    public static GraphicsDto convert(Graphics from) {
        return new GraphicsDto(
                from.getId(),
                from.getBrand(),
                from.getVersion());
    }

    public static List<GraphicsDto> convertList(Set<Graphics> from) {
        return from.stream().map(GraphicsDto::convert).toList();
    }
}
