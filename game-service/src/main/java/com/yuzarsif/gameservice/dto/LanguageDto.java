package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Language;

import java.util.List;
import java.util.Set;

public record LanguageDto(
    Long id,
    String name
) {

    public static LanguageDto convert(Language from) {
        return new LanguageDto(from.getId(), from.getName());
    }

    public static List<LanguageDto> convertList(Set<Language> from) {
        return from.stream().map(LanguageDto::convert).toList();
    }
}
