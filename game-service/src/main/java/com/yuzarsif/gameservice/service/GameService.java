package com.yuzarsif.gameservice.service;

import com.yuzarsif.gameservice.dto.GameDto;
import com.yuzarsif.gameservice.dto.request.CreateGameRequest;
import com.yuzarsif.gameservice.dto.request.SearchByGenreRequest;
import com.yuzarsif.gameservice.exception.EntityNotFoundException;
import com.yuzarsif.gameservice.model.*;
import com.yuzarsif.gameservice.repository.GameRepository;
import com.yuzarsif.gameservice.utils.DateConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final DeveloperService developerService;
    private final FeatureService featureService;
    private final GenreService genreService;
    private final LanguageService languageService;
    private final PlatformService platformService;
    private final PublisherService publisherService;
    private final SystemRequirementService systemRequirementService;

    public GameService(GameRepository gameRepository,
                       DeveloperService developerService,
                       FeatureService featureService,
                       GenreService genreService,
                       LanguageService languageService,
                       PlatformService platformService,
                       PublisherService publisherService,
                       SystemRequirementService systemRequirementService) {
        this.gameRepository = gameRepository;
        this.developerService = developerService;
        this.featureService = featureService;
        this.genreService = genreService;
        this.languageService = languageService;
        this.platformService = platformService;
        this.publisherService = publisherService;
        this.systemRequirementService = systemRequirementService;
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

    public List<GameDto> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return gameRepository.findAll(pageable)
                .stream()
                .map(GameDto::convert)
                .collect(Collectors.toList());
    }

    public List<GameDto> findByNameContaining(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return gameRepository.findByNameContaining(name, pageable)
                .stream()
                .map(GameDto::convert)
                .collect(Collectors.toList());
    }

    public List<GameDto> findByGenres(SearchByGenreRequest searchByGenre) {
        Pageable pageable = PageRequest.of(searchByGenre.page(), searchByGenre.size());

        return gameRepository.findByGenres(searchByGenre.genres(), pageable)
                .stream()
                .map(GameDto::convert)
                .collect(Collectors.toList());
    }

    protected Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game not found with id " + id));
    }
}
