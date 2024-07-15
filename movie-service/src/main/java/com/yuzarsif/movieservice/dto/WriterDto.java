package com.yuzarsif.movieservice.dto;

import com.yuzarsif.movieservice.model.Writer;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record WriterDto(
    Long id,
    String name,
    String birthDate,
    String bio,
    String bornPlace,
    Float height,
    String imageUrl
) {

    public static WriterDto convert(Writer from) {
        return new WriterDto(
            from.getId(),
            from.getName(),
            from.getBirthDate().toString(),
            from.getBio(),
            from.getBornPlace(),
            from.getHeight(),
            from.getUrl());
    }

    public static List<WriterDto> convert(Set<Writer> from) {
        return from.stream().map(WriterDto::convert).toList();
    }
}
