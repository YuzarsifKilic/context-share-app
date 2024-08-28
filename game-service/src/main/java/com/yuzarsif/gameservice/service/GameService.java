package com.yuzarsif.gameservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuzarsif.gameservice.client.SteamClient;
import com.yuzarsif.gameservice.client.response.AppDetailsResponse;
import com.yuzarsif.gameservice.client.response.AppListResponse;
import com.yuzarsif.gameservice.dto.GameDto;
import com.yuzarsif.gameservice.dto.PageResponse;
import com.yuzarsif.gameservice.dto.request.*;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.*;
import com.yuzarsif.gameservice.repository.GameRepository;
import com.yuzarsif.gameservice.utils.DateConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GameService {

    private final GameRepository gameRepository;
    private final DeveloperService developerService;
    private final FeatureService featureService;
    private final GenreService genreService;
    private final LanguageService languageService;
    private final PlatformService platformService;
    private final PublisherService publisherService;
    private final SystemRequirementService systemRequirementService;
    private final SteamClient steamClient;

    public GameService(GameRepository gameRepository,
                       DeveloperService developerService,
                       FeatureService featureService,
                       GenreService genreService,
                       LanguageService languageService,
                       PlatformService platformService,
                       PublisherService publisherService,
                       SystemRequirementService systemRequirementService,
                       SteamClient steamClient) {
        this.gameRepository = gameRepository;
        this.developerService = developerService;
        this.featureService = featureService;
        this.genreService = genreService;
        this.languageService = languageService;
        this.platformService = platformService;
        this.publisherService = publisherService;
        this.systemRequirementService = systemRequirementService;
        this.steamClient = steamClient;
    }

    public void create(CreateGameRequest createGameRequest) {
        Set<Developer> developers = developerService.findByIdList(createGameRequest.developers());
        Set<Feature> features = featureService.findByIdList(createGameRequest.features());
        Set<Genre> genres = genreService.findByIdList(createGameRequest.genres());
        Set<Language> audioLanguages = languageService.findByIdList(createGameRequest.audioLanguages());
        Set<Language> subtitleLanguages = languageService.findByIdList(createGameRequest.subtitleLanguages());
        Set<Platform> platforms = platformService.findByIdList(createGameRequest.platforms());
        Set<Publisher> publishers = publisherService.findByIdList(createGameRequest.publishers());
        SystemRequirement systemRequirement = systemRequirementService.findById(createGameRequest.systemRequirements());

        Game game = Game
                .builder()
                .name(createGameRequest.name())
                .releaseDate(DateConverter.convert(createGameRequest.releaseDate()))
                .description(createGameRequest.description())
                .mainImage(createGameRequest.mainImage())
                .developers(developers)
                .audioLanguages(audioLanguages)
                .subtitleLanguages(subtitleLanguages)
                .features(features)
                .genres(genres)
                .platforms(platforms)
                .publishers(publishers)
                .minSystemRequirement(systemRequirement)
                .build();

        gameRepository.save(game);
    }

    public PageResponse<GameDto> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Game> games = gameRepository.findAll(pageable);
        long startTime = System.currentTimeMillis();
        List<GameDto> gameResponse = gameRepository.findAll(pageable)
                .stream()
                .map(GameDto::convert)
                .toList();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Execution time: " + duration + " milliseconds");
        return new PageResponse<>(
                gameResponse,
                games.getNumber(),
                games.getSize(),
                games.getTotalElements(),
                games.getTotalPages(),
                games.isFirst(),
                games.isLast());
    }

    public PageResponse<GameDto> findByNameContaining(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Game> games = gameRepository.findByNameContaining(name, pageable);

        List<GameDto> gameResponse = games
                .stream()
                .map(GameDto::convert)
                .toList();

        return new PageResponse<>(
                gameResponse,
                games.getNumber(),
                games.getSize(),
                games.getTotalElements(),
                games.getTotalPages(),
                games.isFirst(),
                games.isLast());
    }

    public PageResponse<GameDto> findByGenres(SearchByGenreRequest searchByGenre) {
        Pageable pageable = PageRequest.of(searchByGenre.page(), searchByGenre.size());

        Page<Game> games = gameRepository.findByGenres(searchByGenre.genres(), pageable);

        List<GameDto> gameResponse = gameRepository.findByGenres(searchByGenre.genres(), pageable)
                .stream()
                .map(GameDto::convert)
                .toList();

        return new PageResponse<>(
                gameResponse,
                games.getNumber(),
                games.getSize(),
                games.getTotalElements(),
                games.getTotalPages(),
                games.isFirst(),
                games.isLast());
    }

    protected Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id " + id));
    }

    public GameDto getById(Long id) {
        return GameDto.convert(findById(id));
    }

}
