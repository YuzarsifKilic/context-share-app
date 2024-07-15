package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Processor;

import java.util.List;
import java.util.Set;

public record ProcessorDto(
    Long id,
    String brand,
    String version
) {

    public static ProcessorDto convert(Processor from) {
        return new ProcessorDto(
                from.getId(),
                from.getBrand(),
                from.getVersion());
    }

    public static List<ProcessorDto> convertList(Set<Processor> from) {
        return from.stream().map(ProcessorDto::convert).toList();
    }
}
