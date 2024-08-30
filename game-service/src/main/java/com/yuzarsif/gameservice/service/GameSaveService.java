package com.yuzarsif.gameservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuzarsif.gameservice.client.SteamClient;
import com.yuzarsif.gameservice.client.response.AppDetailsResponse;
import com.yuzarsif.gameservice.client.response.AppListResponse;
import com.yuzarsif.gameservice.dto.request.*;
import com.yuzarsif.gameservice.model.*;
import com.yuzarsif.gameservice.model.Currency;
import com.yuzarsif.gameservice.repository.jpa.GameRepository;
import com.yuzarsif.gameservice.utils.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameSaveService {

    private final GameRepository gameRepository;
    private final SteamClient steamClient;
    private final DeveloperService developerService;
    private final PublisherService publisherService;
    private final FeatureService featureService;
    private final GenreService genreService;
    private final SystemRequirementService systemRequirementService;
    private final StoreService storeService;
    private final CheckedGameService checkedGameService;
    private final LanguageService languageService;
    private volatile boolean running = false;
    private Thread clientThread;

    public void startClient() {
        running = true;
        clientThread = new Thread(() -> {
            while (running) {
                try {
                    this.saveGamesBySteamClient();
                } catch (JsonProcessingException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        clientThread.start();
    }

    public void stopClient() {
        running = false;
        if (clientThread != null) {
            clientThread.interrupt();
        }
    }

    public void saveGamesBySteamClient() throws JsonProcessingException, InterruptedException {
        AppListResponse apps = steamClient.getApps();

        for (AppListResponse.App app: apps.getApplist().getApps()) {
            log.info("Check game: " + app.getName());
            Optional<Game> optionalGame = gameRepository.findByName(app.getName());
            if (optionalGame.isEmpty() && !Objects.equals(app.getName(), "")) {
                if (!checkedGameService.existsByGameId(app.getAppid())) {
                    log.info("Game didnt check before: " + app.getName());
                    AppDetailsResponse appDetails = steamClient.getAppDetails(app.getAppid().toString());
                    CheckedGame checkedGame = new CheckedGame();
                    checkedGame.setGameId(app.getAppid());
                    checkedGame.setPlatform("Steam");
                    if (appDetails != null && !appDetails.getData().getRelease_date().coming_soon && appDetails.getData().getType().equals("game")) {
                        log.info("Game creating... " + app.getName());
                        Game game = Game
                                .builder()
                                .name(app.getName())
                                .description(appDetails.getData().getAbout_the_game())
                                .build();
                        if (appDetails.getData().getHeader_image() != null && !appDetails.getData().getHeader_image().isEmpty()) {
                            game.setMainImage(appDetails.getData().getHeader_image());
                        } else {
                            checkedGame.setMainImageEmpty(true);
                        }
                        if (!appDetails.getData().getRelease_date().coming_soon && appDetails.getData().getRelease_date().date != null && !appDetails.getData().getRelease_date().date.isEmpty() && appDetails.getData().getRelease_date().date.length() > 8) {
                            game.setReleaseDate(DateConverter.steamDateToJavaDate(appDetails.getData().getRelease_date().getDate()));
                        } else {
                            checkedGame.setReleaseDateEmpty(true);
                        }

                        if (appDetails.getData().getDevelopers() != null && !appDetails.getData().getDevelopers().isEmpty()) {
                            Set<Developer> developerSet = new HashSet<>();
                            ArrayList<String> developers = appDetails.getData().getDevelopers();
                            for (String developer: developers) {
                                if (developerService.findByName(developer).isEmpty()) {
                                    Developer savedDeveloper = developerService.create(new CreateDeveloperRequest(developer));
                                    developerSet.add(savedDeveloper);
                                }
                            }
                            game.setDevelopers(developerSet);
                            if (developerSet.isEmpty()) {
                                checkedGame.setDeveloperEmpty(true);
                            }
                        }

                        if (appDetails.getData().getPublishers() != null && !appDetails.getData().getPublishers().isEmpty()) {
                            Set<Publisher> publisherSet = new HashSet<>();
                            ArrayList<String> publishers = appDetails.getData().getPublishers();
                            for (String publisher: publishers) {
                                if (publisherService.findByName(publisher).isEmpty()) {
                                    Publisher savedPublisher = publisherService.create(new CreatePublisherRequest(publisher));
                                    publisherSet.add(savedPublisher);
                                }
                            }
                            game.setPublishers(publisherSet);
                            if (publisherSet.isEmpty()) {
                                checkedGame.setPublisherEmpty(true);
                            }
                        }

                        if (appDetails.getData().getCategories() != null && !appDetails.getData().getCategories().isEmpty()) {
                            ArrayList<AppDetailsResponse.Category> categories = appDetails.getData().getCategories();
                            Set<Feature> featureSet = new HashSet<>();
                            for (AppDetailsResponse.Category category: categories) {
                                if (featureService.findByName(category.getDescription()).isEmpty()) {
                                    Feature savedFeature = featureService.create(new CreateFeatureRequest(category.getDescription()));
                                    featureSet.add(savedFeature);
                                }
                            }
                            game.setFeatures(featureSet);
                            if (featureSet.isEmpty()) {
                                checkedGame.setFeatureEmpty(true);
                            }
                        }

                        if (appDetails.getData().getGenres() != null && !appDetails.getData().getGenres().isEmpty()) {
                            ArrayList<AppDetailsResponse.Genre> genres = appDetails.getData().getGenres();
                            Set<Genre> genreSet = new HashSet<>();
                            for (AppDetailsResponse.Genre genre: genres) {
                                if (genreService.findByName(genre.getDescription()).isEmpty()) {
                                    Genre savedGenre = genreService.create(new CreateGenreRequest(genre.getDescription()));
                                    genreSet.add(savedGenre);
                                }
                            }
                            game.setGenres(genreSet);
                            if (genreSet.isEmpty()) {
                                checkedGame.setGenreEmpty(true);
                            }
                        }

                        if (appDetails.getData().getSupported_languages() != null && !appDetails.getData().getSupported_languages().isEmpty()) {
                            Set<Language> audioLanguageList = languageService.extractAudioLanguageList(appDetails.getData().getSupported_languages(), checkedGame);
                            Set<Language> subtitleLanguageList = languageService.extractSubtitleLanguageList(appDetails.getData().getSupported_languages(), checkedGame);
                            if (!audioLanguageList.isEmpty()) {
                                game.setAudioLanguages(audioLanguageList);
                            }
                            if (!subtitleLanguageList.isEmpty()) {
                                game.setSubtitleLanguages(subtitleLanguageList);
                            }
                        }

                        if (appDetails.getData().getPc_requirements() != null && appDetails.getData().getPc_requirements().minimum != null) {
                            SystemRequirement systemRequirement = systemRequirementService.extractSystemRequirements(appDetails.getData().getPc_requirements().minimum, checkedGame);

                            if (systemRequirement != null) {
                                game.setMinSystemRequirement(systemRequirement);
                            } else {
                                checkedGame.setSystemRequirementsEmpty(true);
                            }
                        }

                        Game savedGame = gameRepository.save(game);
                        log.info("Game created: " + app.getName() + " id: " + savedGame.getId());

                        String gameUrl = "https://store.steampowered.com/app/" + app.getAppid();
                        Float price = 0f;
                        String currency = null;
                        if (appDetails.getData().getPrice_overview() != null) {
                            price = (float) appDetails.getData().getPrice_overview().initial / 100;
                            currency = appDetails.getData().getPrice_overview().currency;
                        }
                        storeService.createStore(new CreateStoreRequest(StoreType.STEAM, price, Currency.USD.toString(), gameUrl, savedGame.getId(), 1L));
                        checkedGameService.save(checkedGame);

                        Thread.sleep(5000);
                    } else {
                        checkedGame.setNameEmpty(true);
                        checkedGameService.save(checkedGame);
                    }
                } else {
                    log.error("Game is already checked " + app.getAppid());
                }
            } else {
                log.error("Game is already saved or name is empty: " + app.getName());
            }
        }
    }


}
