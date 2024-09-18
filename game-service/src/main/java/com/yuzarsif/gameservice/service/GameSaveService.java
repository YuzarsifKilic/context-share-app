package com.yuzarsif.gameservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yuzarsif.gameservice.client.epic.EpicClient;
import com.yuzarsif.gameservice.client.epic.response.*;
import com.yuzarsif.gameservice.client.steam.SteamClient;
import com.yuzarsif.gameservice.client.steam.response.AppDetailsResponse;
import com.yuzarsif.gameservice.client.steam.response.AppListResponse;
import com.yuzarsif.gameservice.dto.request.*;
import com.yuzarsif.gameservice.model.*;
import com.yuzarsif.gameservice.model.Currency;
import com.yuzarsif.gameservice.repository.GameRepository;
import com.yuzarsif.gameservice.repository.StoreRepository;
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
    private final EpicClient epicClient;
    private final PlatformService platformService;
    private final StoreRepository storeRepository;
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
                if (! checkedGameService.existsByGameId(String.valueOf(app.getAppid()))) {
                    log.info("Game didnt check before: " + app.getName());
                    AppDetailsResponse appDetails = steamClient.getAppDetails(app.getAppid().toString());
                    CheckedGame checkedGame = new CheckedGame();
                    checkedGame.setGameId(String.valueOf(app.getAppid()));
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

                        // TODO: set memory and storage

                        if (appDetails.getData().getPc_requirements() != null && appDetails.getData().getPc_requirements().minimum != null) {
                            SystemRequirement systemRequirement = systemRequirementService.extractSystemRequirements(appDetails.getData().getPc_requirements().minimum, checkedGame);

                            if (systemRequirement != null) {
                                game.setMinSystemRequirement(systemRequirement);
                            } else {
                                checkedGame.setSystemRequirementsEmpty(true);
                            }
                        }

                        // TODO: set memory and storage

                        if (appDetails.getData().getPc_requirements() != null && appDetails.getData().getPc_requirements().recommended != null) {
                            log.info("recommended requirements: " + appDetails.getData().getPc_requirements().recommended);
                            SystemRequirement systemRequirement = systemRequirementService.extractSystemRequirements(appDetails.getData().getPc_requirements().recommended, checkedGame);

                            if (systemRequirement != null) {
                                game.setRecommendedSystemRequirement(systemRequirement);
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

    public void startEpicGames() throws InterruptedException {
        Integer total = 0;
        StoreList storeList = epicClient.getStoreList(40, 0);
        while (total < storeList.data.catalog.searchStore.paging.total) {
            StoreList storeList1 = epicClient.getStoreList(40, total);
            for (StoreList.Element store: storeList1.data.catalog.searchStore.elements) {
                CatalogOfferResponse catalogOffer = epicClient.getCatalogOffer("en", store.id, store.namespace, "US");
                if (checkedGameService.existsByGameId(store.id)) {
//                    CheckedGame checkedGame = new CheckedGame();
//                    checkedGame.setGameId(store.id);
//                    checkedGame.setPlatform("EPIC");
//                    checkedGameService.save(checkedGame);

                    log.info("Game created: " + catalogOffer.data.catalog.catalogOffer.title + " id: " + store.id);

                    if (gameRepository.findByName(store.title).isEmpty()) {
                        Game game = Game
                                .builder()
                                .name(store.title)
                                .releaseDate(DateConverter.epicDateToJavaDate(store.releaseDate))
                                .build();

                        for (StoreList.KeyImage image: store.keyImages) {
                            if (Objects.equals(image.type, "Thumbnail")) {
                                game.setMainImage(image.url);
                            }
                        }

                        log.info(String.valueOf(store.offerMappings));
                        if (!store.offerMappings.isEmpty() && store.offerMappings.get(0).pageSlug != null) {
                            //TODO: extract description - clear description responsex

                            StorePageMappingResponse storePageMapping = epicClient.getStorePageMapping(store.offerMappings.get(0).pageSlug, "en-US");

                            ProductResponse product = epicClient.getProduct(storePageMapping.data.storePageMapping.mapping.productId);

                            Set<Developer> developers = new HashSet<>();
                            for (String developer: product.developers) {
                                if (developerService.findByName(developer).isEmpty()) {
                                    Developer savedDeveloper = developerService.create(new CreateDeveloperRequest(developer));
                                    developers.add(savedDeveloper);
                                } else {
                                    developerService.findByName(developer).ifPresent(developers::add);
                                }
                            }

                            game.setDevelopers(developers);

                            Set<Publisher> publishers = new HashSet<>();
                            for (String publisher: product.publishers) {
                                if (publisherService.findByName(publisher).isEmpty()) {
                                    Publisher savedPublisher = publisherService.create(new CreatePublisherRequest(publisher));
                                    publishers.add(savedPublisher);
                                } else {
                                    publisherService.findByName(publisher).ifPresent(publishers::add);
                                }
                            }

                            game.setPublishers(publishers);

                            Set<Feature> features = new HashSet<>();
                            for (ProductResponse.Feature feature: product.tags.features) {
                                if (featureService.findByName(feature.name).isEmpty()) {
                                    Feature savedFeature = featureService.create(new CreateFeatureRequest(feature.name));
                                    features.add(savedFeature);
                                } else {
                                    featureService.findByName(feature.name).ifPresent(features::add);
                                }
                            }

                            game.setFeatures(features);

                            Set<Genre> genres = new HashSet<>();
                            for (ProductResponse.Genre genre: product.tags.genres) {
                                if (genreService.findByName(genre.name).isEmpty()) {
                                    Genre savedGenre = genreService.create(new CreateGenreRequest(genre.name));
                                    genres.add(savedGenre);
                                } else {
                                    genreService.findByName(genre.name).ifPresent(genres::add);
                                }
                            }

                            game.setGenres(genres);

                            Set<Platform> platforms = new HashSet<>();
                            for (ProductResponse.Platform platform: product.tags.platforms) {
                                if (platformService.findByName(platform.name).isEmpty()) {
                                    Platform savedPlatform = platformService.create(new CreatePlatformRequest(platform.name));
                                    platforms.add(savedPlatform);
                                } else {
                                    platformService.findByName(platform.name).ifPresent(platforms::add);
                                }
                            }

                            game.setPlatforms(platforms);

                            ProductConfigurationResponse productConfiguration = epicClient.getProductConfiguration(store.namespace);

                            Set<Language> audioLanguages = new HashSet<>();
                            for (Object audioLanguage: productConfiguration.data.product.sandbox.configuration.get(0).configs.supportedAudio) {
                                if (languageService.findByName((String) audioLanguage).isEmpty()) {
                                    Language savedLanguage = languageService.saveLanguage((String) audioLanguage);
                                    audioLanguages.add(savedLanguage);
                                } else {
                                    languageService.findByName((String) audioLanguage).ifPresent(audioLanguages::add);
                                }
                            }

                            game.setAudioLanguages(audioLanguages);

                            Set<Language> subtitleLanguages = new HashSet<>();
                            for (String audioLanguage: productConfiguration.data.product.sandbox.configuration.get(0).configs.supportedText) {
                                if (languageService.findByName(audioLanguage).isEmpty()) {
                                    Language savedLanguage = languageService.saveLanguage(audioLanguage);
                                    subtitleLanguages.add(savedLanguage);
                                } else {
                                    languageService.findByName(audioLanguage).ifPresent(subtitleLanguages::add);
                                }
                            }

                            game.setSubtitleLanguages(subtitleLanguages);

                            if (productConfiguration.data.product.sandbox.configuration.get(0).configs.technicalRequirements.windows != null) {
                                 game.setMinSystemRequirement(systemRequirementService.extractMinSystemRequirementsEpic(productConfiguration.data.product.sandbox.configuration.get(0).configs.technicalRequirements.windows));
                                 game.setRecommendedSystemRequirement(systemRequirementService.extractRecommendedSystemRequirementsEpic(productConfiguration.data.product.sandbox.configuration.get(0).configs.technicalRequirements.windows));
                            }

                            Description productDescription = epicClient.getProductDescription(catalogOffer.data.catalog.catalogOffer.namespace);

                            log.info("Description : " + productDescription.data.product.sandbox.configuration.get(1).configs.longDescription);

                            if (productDescription.data.product.sandbox.configuration.get(1).configs.longDescription != null) {
                                game.setDescription(productDescription.data.product.sandbox.configuration.get(1).configs.longDescription);
                            }

                            Game savedGame = gameRepository.save(game);

                            // TODO: fix price issue

                            Store gameStore = Store
                                    .builder()
                                    .game(savedGame)
                                    .currency(Currency.valueOf(catalogOffer.data.catalog.catalogOffer.price.totalPrice.currencyCode.toUpperCase(Locale.ROOT)))
                                    .storeName(StoreType.EPIC)
                                    .finalPrice((float) (catalogOffer.data.catalog.catalogOffer.price.totalPrice.originalPrice/100.0f))
                                    .url("https://store.epicgames.com/en-US/p/" + store.offerMappings.get(0).pageSlug)
                                    .build();
                            storeRepository.save(gameStore);

                            log.info("Store saved");
                            Thread.sleep(1000);
                        }

                    }
                }
            }
            System.out.println("****");
            System.out.println("total: " + storeList1.data.catalog.searchStore.paging.total);
            total += 40;
        }
    }


}
