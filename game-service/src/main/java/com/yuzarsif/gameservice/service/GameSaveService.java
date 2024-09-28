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
import org.springframework.scheduling.annotation.Async;
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
    private final VideoService videoService;
    private final PhotoService photoService;
    private volatile boolean running = false;
    private Thread clientThread;

    public void startClient() {
        running = true;
        clientThread = new Thread(() -> {
            while (running) {
//                try {
//                    //this.saveGameAsync();
//                } catch (JsonProcessingException | InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
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

        for (AppListResponse.App app : apps.getApplist().getApps()) {
            if (!app.getName().isEmpty()) {
                log.info("Check game: " + app.getName());
                saveGameAsync(app);
                Thread.sleep(1000);
            }
        }
    }


    @Async
    public void saveGameAsync(AppListResponse.App app) throws JsonProcessingException, InterruptedException {
        Optional<Game> optionalGame = gameRepository.findByName(app.getName());
        if (optionalGame.isEmpty()) {
            log.info("Game didnt check before: " + app.getName());
            AppDetailsResponse appDetails = steamClient.getAppDetails(app.getAppid().toString());
            if (appDetails != null && !appDetails.getData().getRelease_date().coming_soon && appDetails.getData().getType().equals("game")) {
                log.info("Game creating... " + app.getName());
                Game game = Game
                        .builder()
                        .name(app.getName())
                        .description(appDetails.getData().getAbout_the_game())
                        .commentCount(0)
                        .build();

                if (!appDetails.getData().getRelease_date().coming_soon && appDetails.getData().getRelease_date().date != null && !appDetails.getData().getRelease_date().date.isEmpty() && appDetails.getData().getRelease_date().date.length() > 8) {
                    game.setReleaseDate(DateConverter.steamDateToJavaDate(appDetails.getData().getRelease_date().getDate()));
                }

                if (appDetails.getData().getDevelopers() != null && !appDetails.getData().getDevelopers().isEmpty()) {
                    log.info("Developers: " + appDetails.getData().getDevelopers());
                    Set<Developer> developerSet = new HashSet<>();
                    for (String developer: appDetails.getData().getDevelopers()) {
                        developerService.findByName(developer).ifPresentOrElse(developerSet::add, () -> {
                            Developer savedDeveloper = developerService.create(new CreateDeveloperRequest(developer));
                            developerSet.add(savedDeveloper);
                        });
                    }
                    game.setDevelopers(developerSet);
                }

                if (appDetails.getData().getPublishers() != null && !appDetails.getData().getPublishers().isEmpty()) {
                    log.info("Publishers: " + appDetails.getData().getPublishers());
                    Set<Publisher> publisherSet = new HashSet<>();
                    for (String publisher : appDetails.getData().getPublishers()) {
                        publisherService.findByName(publisher).ifPresentOrElse(publisherSet::add, () -> {
                            Publisher savedPublisher = publisherService.create(new CreatePublisherRequest(publisher));
                            publisherSet.add(savedPublisher);
                        });
                    }
                    game.setPublishers(publisherSet);
                }

                if (appDetails.getData().getCategories() != null && !appDetails.getData().getCategories().isEmpty()) {
                    log.info("Categories: " + appDetails.getData().getCategories());
                    Set<Feature> featureSet = new HashSet<>();
                    for (AppDetailsResponse.Category category : appDetails.getData().getCategories()) {
                        featureService.findByName(category.getDescription()).ifPresentOrElse(featureSet::add, () -> {
                            Feature savedFeature = featureService.create(new CreateFeatureRequest(category.getDescription()));
                            featureSet.add(savedFeature);
                        });
                    }
                    game.setFeatures(featureSet);
                }

                if (appDetails.getData().getGenres() != null && !appDetails.getData().getGenres().isEmpty()) {
                    log.info("Genres: " + appDetails.getData().getGenres());
                    Set<Genre> genreSet = new HashSet<>();
                    for (AppDetailsResponse.Genre genre : appDetails.getData().getGenres()) {
                        genreService.findByName(genre.getDescription()).ifPresentOrElse(genreSet::add, () -> {
                            Genre savedGenre = genreService.create(new CreateGenreRequest(genre.getDescription()));
                            genreSet.add(savedGenre);
                        });
                    }
                    game.setGenres(genreSet);
                }

                if (appDetails.getData().getSupported_languages() != null && !appDetails.getData().getSupported_languages().isEmpty()) {
                    System.out.println(appDetails.getData().getSupported_languages());
                    Set<Language> audioLanguageList = languageService.extractAudioLanguageList(appDetails.getData().getSupported_languages());
                    Set<Language> subtitleLanguageList = languageService.extractSubtitleLanguageList(appDetails.getData().getSupported_languages());
                    if (!audioLanguageList.isEmpty()) {
                        game.setAudioLanguages(audioLanguageList);
                    }
                    if (!subtitleLanguageList.isEmpty()) {
                        game.setSubtitleLanguages(subtitleLanguageList);
                    }
                    log.info("languages: " + audioLanguageList + " " + subtitleLanguageList);
                }

                Set<Platform> platforms = new HashSet<>();

                if (appDetails.getData().getPlatforms() != null) {
                    if (appDetails.getData().getPlatforms().windows) {
                        if (platformService.findByName("Windows").isEmpty()) {
                            Platform savedPlatform = platformService.create(new CreatePlatformRequest("Windows"));
                            platforms.add(savedPlatform);
                        } else {
                            platforms.add(platformService.findByName("Windows").get());
                        }
                    }
                    if (appDetails.getData().getPlatforms().mac) {
                        if (platformService.findByName("Mac").isEmpty()) {
                            Platform savedPlatform = platformService.create(new CreatePlatformRequest("Mac"));
                            platforms.add(savedPlatform);
                        } else {
                            platforms.add(platformService.findByName("Mac").get());
                        }
                    }
                    if (appDetails.getData().getPlatforms().linux) {
                        if (platformService.findByName("Linux").isEmpty()) {
                            Platform savedPlatform = platformService.create(new CreatePlatformRequest("Linux"));
                            platforms.add(savedPlatform);
                        } else {
                            platforms.add(platformService.findByName("Linux").get());
                        }
                    }
                    game.setPlatforms(platforms);

                }

                game.setPlatforms(platforms);

                if (appDetails.getData().getPc_requirements() != null && appDetails.getData().getPc_requirements().minimum != null) {
                    SystemRequirement systemRequirement = systemRequirementService.extractSystemRequirements(appDetails.getData().getPc_requirements().minimum);

                    if (systemRequirement != null) {
                        game.setMinSystemRequirement(systemRequirement);
                    }
                }

                if (appDetails.getData().getPc_requirements() != null && appDetails.getData().getPc_requirements().recommended != null) {
                    log.info("recommended requirements: " + appDetails.getData().getPc_requirements().recommended);
                    SystemRequirement systemRequirement = systemRequirementService.extractSystemRequirements(appDetails.getData().getPc_requirements().recommended);

                    if (systemRequirement != null) {
                        game.setRecommendedSystemRequirement(systemRequirement);
                    }
                }

                Game savedGame = gameRepository.save(game);

                if (appDetails.getData().getHeader_image() != null && !appDetails.getData().getHeader_image().isEmpty()) {
                    Photo photo = new Photo();
                    photo.setType("main");
                    photo.setThumbnailUrl(appDetails.getData().getHeader_image());
                    photo.setGame(savedGame);
                    photoService.createPhoto(photo);
                    savedGame.setMainImage(appDetails.getData().getHeader_image());
                    gameRepository.save(savedGame);
                }

                if (appDetails.getData().getScreenshots() != null && !appDetails.getData().getScreenshots().isEmpty()) {
                    int index = 1;
                    for (AppDetailsResponse.Screenshot screenshot : appDetails.getData().getScreenshots()) {
                        Photo photo = new Photo();
                        photo.setType("screenshot_" + index);
                        photo.setThumbnailUrl(screenshot.path_thumbnail);
                        photo.setFullUrl(screenshot.path_full);
                        photo.setGame(savedGame);
                        photoService.createPhoto(photo);
                        index++;
                    }
                }

                if (appDetails.getData().getMovies() != null && !appDetails.getData().getMovies().isEmpty()) {
                    Video video = new Video();
                    video.setVideoUrl(appDetails.getData().getMovies().get(0).getWebm().getMax());
                    video.setGame(savedGame);
                    video.setThumbnailUrl(appDetails.getData().getMovies().get(0).getThumbnail());
                    videoService.createVideo(video);
                }
                log.info("Game created: " + app.getName() + " id: " + savedGame.getId());

                String gameUrl = "https://store.steampowered.com/app/" + app.getAppid();
                Float price = 0f;
                String currency = null;
                if (appDetails.getData().getPrice_overview() != null) {
                    price = (float) appDetails.getData().getPrice_overview().initial / 100;
                    currency = appDetails.getData().getPrice_overview().currency;
                }
                storeService.createStore(new CreateStoreRequest(StoreType.STEAM, price, Currency.USD.toString(), gameUrl, savedGame.getId()));

                Thread.sleep(3000);
            }
        } else {
            log.info("Game already exists: " + app.getName());
            log.info("Checking store ...");
            Boolean checkStore = storeService.checkStore(app.getName(), StoreType.STEAM);
            if (!checkStore) {
                log.info("Creating store ...");
                AppDetailsResponse appDetails = steamClient.getAppDetails(app.getAppid().toString());
                String gameUrl = "https://store.steampowered.com/app/" + app.getAppid();
                Float price = 0f;
                String currency = null;
                if (appDetails.getData().getPrice_overview() != null) {
                    price = (float) appDetails.getData().getPrice_overview().initial / 100;
                    currency = appDetails.getData().getPrice_overview().currency;
                }
                storeService.createStore(new CreateStoreRequest(StoreType.STEAM, price, Currency.USD.toString(), gameUrl, optionalGame.get().getId()));
            } else {
                log.info("Store already exists: " + app.getName());
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

                    if (gameRepository.findByName(store.title).isEmpty()) {
                        Game game = Game
                                .builder()
                                .name(store.title)
                                .releaseDate(DateConverter.epicDateToJavaDate(store.releaseDate))
                                .commentCount(0)
                                .build();

                        for (StoreList.KeyImage image: store.keyImages) {
                            if (Objects.equals(image.type, "Thumbnail")) {
                                //game.setMainImage(image.url);
                            }
                        }

                        log.info(String.valueOf(store.offerMappings));
                        if (!store.offerMappings.isEmpty() && store.offerMappings.get(0).pageSlug != null) {

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
                            log.info("Platforms : " + product.tags.platforms);
                            for (ProductResponse.Platform platform: product.tags.platforms) {
                                if (Objects.equals(platform.name, "Windows")) {
                                    if (platformService.findByName("Windows").isEmpty()) {
                                        Platform savedPlatform = platformService.create(new CreatePlatformRequest("Windows"));
                                        platforms.add(savedPlatform);
                                    } else {
                                        platformService.findByName("Windows").ifPresent(platforms::add);
                                    }
                                } if (Objects.equals(platform.name, "Linux")) {
                                    if (platformService.findByName("Linux").isEmpty()) {
                                        Platform savedPlatform = platformService.create(new CreatePlatformRequest("Linux"));
                                        platforms.add(savedPlatform);
                                    } else {
                                        platformService.findByName("Linux").ifPresent(platforms::add);
                                    }
                                } if (Objects.equals(platform.name, "Mac")) {
                                    if (platformService.findByName("Mac").isEmpty()) {
                                        Platform savedPlatform = platformService.create(new CreatePlatformRequest("Mac"));
                                        platforms.add(savedPlatform);
                                    } else {
                                        platformService.findByName("Mac").ifPresent(platforms::add);
                                    }
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


                            if (productDescription.data.product.sandbox.configuration.get(1).configs.longDescription != null) {
                                game.setDescription(productDescription.data.product.sandbox.configuration.get(1).configs.longDescription);
                            }

                            ProductHomeConfig productHomeConfig = epicClient.getProductHomeConfig(store.namespace);

                            Game savedGame = gameRepository.save(game);

                            String videoId = null;


                            for (ProductHomeConfig.KeyImage keyImage: productHomeConfig.data.product.sandbox.configuration.get(1).configs.keyImages) {
                                if (keyImage.type.equals("heroCarouselVideo")) {
                                    videoId = keyImage.url.substring(keyImage.url.indexOf("://") + 3, keyImage.url.indexOf("?"));
                                }
                            }

                            String mediaRefId = null;

                            if (videoId != null) {
                                VideoByIdResponse videoById = epicClient.getVideoById(videoId);
                                if (!videoById.data.video.fetchVideoByLocale.isEmpty()) {
                                    for (VideoByIdResponse.FetchVideoByLocale fetchVideoByLocale: videoById.data.video.fetchVideoByLocale) {
                                        if (fetchVideoByLocale.recipe.equals("video-fmp4")) {
                                            mediaRefId = fetchVideoByLocale.mediaRefId;
                                        }
                                    }
                                }
                            }

                            String videoUrl = null;
                            String audioUrl = null;
                            String thumbnailUrl = null;

                            if (mediaRefId != null) {
                                GetVideoResponse video = epicClient.getVideo(mediaRefId);
                                if (video.data != null && video.data.media != null && video.data.media.getMediaRef != null && !video.data.media.getMediaRef.outputs.isEmpty()) {
                                    for (GetVideoResponse.Output output: video.data.media.getMediaRef.outputs) {
                                        if (output.contentType.equals("video/mp4") && output.key.equals("high")) {
                                            videoUrl = output.url;
                                        } if (videoUrl == null && output.contentType.equals("audio/mp4") && output.key.equals("medium")) {
                                            videoUrl = output.url;
                                        } if (videoUrl == null && output.contentType.equals("audio/mp4") && output.key.equals("low")) {
                                            videoUrl = output.url;
                                        } if (output.contentType.equals("audio/m4a")) {
                                            audioUrl = output.url;
                                        } if (output.key.equals("thumbnail")) {
                                            thumbnailUrl = output.url;
                                        }
                                    }
                                }
                            }

                            if (videoUrl != null) {
                                Video video = Video
                                        .builder()
                                        .videoUrl(videoUrl)
                                        .build();
                                if (audioUrl != null) {
                                    video.setAudioUrl(audioUrl);
                                }
                                if (thumbnailUrl != null) {
                                    video.setThumbnailUrl(thumbnailUrl);
                                }
                                video.setGame(savedGame);
                                videoService.createVideo(video);
                            }

                            for (CatalogOfferResponse.KeyImage keyImage: catalogOffer.data.catalog.catalogOffer.keyImages) {
                                log.info(keyImage.toString());
                                if (keyImage.type.equals("Thumbnail")) {
                                    Photo photo = Photo
                                            .builder()
                                            .thumbnailUrl(keyImage.url)
                                            .type("main")
                                            .game(savedGame)
                                            .build();
                                    photoService.createPhoto(photo);
                                    savedGame.setMainImage(keyImage.url);
                                    gameRepository.save(savedGame);
                                }
                                if (keyImage.type.equals("featuredMedia")) {
                                    Photo photo = Photo
                                            .builder()
                                            .fullUrl(keyImage.url)
                                            .game(savedGame)
                                            .type("featuredMedia")
                                            .build();

                                    photoService.createPhoto(photo);
                                }
                            }

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
                        }

                    } else {
                        log.info("Game already saved");
                        log.info("Checking for epic store...");
                        if (!storeService.checkStore(store.title, StoreType.EPIC)) {
                            log.info("Game is not found on epic store. Creating new store...");
                            Optional<Game> optionalGame = gameRepository.findByName(store.title);
                            Store gameStore = Store
                                    .builder()
                                    .game(optionalGame.get())
                                    .currency(Currency.valueOf(catalogOffer.data.catalog.catalogOffer.price.totalPrice.currencyCode.toUpperCase(Locale.ROOT)))
                                    .storeName(StoreType.EPIC)
                                    .finalPrice((float) (catalogOffer.data.catalog.catalogOffer.price.totalPrice.originalPrice/100.0f))
                                    .url("https://store.epicgames.com/en-US/p/" + store.offerMappings.get(0).pageSlug)
                                    .build();
                            storeRepository.save(gameStore);
                        } else {
                            log.info("Game is found on epic store. Passing game.");
                        }
                    }
            }
            System.out.println("****");
            System.out.println("total: " + storeList1.data.catalog.searchStore.paging.total);
            total += 40;
        }
    }


}
