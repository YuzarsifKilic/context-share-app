package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Game;
import com.yuzarsif.gameservice.model.Store;
import com.yuzarsif.gameservice.model.SystemRequirement;

import java.util.List;

public record GameDto(
    Long id,
    String name,
    String description,
    String releaseDate,
    List<DeveloperDto> developers,
    List<GenreDto> genres,
    List<FeatureDto> features,
    List<PlatformDto> platforms,
    List<LanguageDto> audioLanguages,
    List<LanguageDto> subtitleLanguages,
    SystemRequirementDto minSystemRequirement,
    List<StoreDto> stores
) {

    public static GameDto convert(Game from) {
        return new GameDto(
                from.getId(),
                from.getName(),
                from.getDescription(),
                from.getReleaseDate().toString(),
                DeveloperDto.convertList(from.getDevelopers()),
                GenreDto.convertList(from.getGenres()),
                FeatureDto.convertList(from.getFeatures()),
                PlatformDto.convertList(from.getPlatforms()),
                LanguageDto.convertList(from.getAudioLanguages()),
                LanguageDto.convertList(from.getSubtitleLanguages()),
                SystemRequirementDto.convert(from.getMinSystemRequirement()),
                StoreDto.convertList(from.getStores()));
    }
}
