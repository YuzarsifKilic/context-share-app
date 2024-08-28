package com.yuzarsif.gameservice.dto;

import com.yuzarsif.gameservice.model.Game;

import java.util.List;

public record GameListDto(
        Long id,
        String name,
        String mainImage
) {

    public static GameListDto convert(Game game) {
        return new GameListDto(
                game.getId(),
                game.getName(),
                game.getMainImage()
        );
    }

    public static List<GameListDto> convertList(List<Game> games) {
        return games.stream().map(GameListDto::convert).toList();
    }
}
