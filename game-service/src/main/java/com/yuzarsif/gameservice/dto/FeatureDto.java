package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Feature;

import java.util.List;
import java.util.Set;

public record FeatureDto(
    Long id,
    String name
) {

    public static FeatureDto convert(Feature from) {
        return new FeatureDto(from.getId(), from.getName());
    }

    public static List<FeatureDto> convertList(Set<Feature> from) {
        return from.stream().map(FeatureDto::convert).toList();
    }
}
