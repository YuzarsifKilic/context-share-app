package com.yuzarsif.gameservice;

import com.yuzarsif.gameservice.client.epic.EpicClient;
import com.yuzarsif.gameservice.client.epic.response.CatalogOfferResponse;
import com.yuzarsif.gameservice.client.epic.response.StoreList;
import com.yuzarsif.gameservice.client.epic.response.StorePageMappingResponse;
import com.yuzarsif.gameservice.client.steam.SteamClient;
import com.yuzarsif.gameservice.model.Platform;
import com.yuzarsif.gameservice.repository.PlatformRepository;
import com.yuzarsif.gameservice.service.GameSaveService;
import com.yuzarsif.gameservice.service.GameService;
import com.yuzarsif.gameservice.service.SystemRequirementService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class GameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(GameService gameService,
                                               SteamClient steamClient,
                                               SystemRequirementService systemRequirementService,
                                               GameSaveService gameSaveService,
                                               PlatformRepository platformRepository,
                                               EpicClient epicClient) {
        return args -> {
            Optional<Platform> steam = platformRepository.findByName("STEAM");
            if (steam.isEmpty()) {
                Platform platform = Platform
                        .builder()
                        .name("STEAM")
                        .build();
                platformRepository.save(platform);
            }

            Optional<Platform> epic = platformRepository.findByName("EPIC");
            if (epic.isEmpty()) {
                Platform platform = Platform
                        .builder()
                        .name("EPIC")
                        .build();
                platformRepository.save(platform);
            }
            //gameSaveService.saveGamesBySteamClient();

//            CatalogOfferResponse catalogOffer = epicClient.getCatalogOffer("en", "e6958dc0e1fe43a38cca9eab6bb96aca", "014e225d587d41ea80c0adb3f33041d0", "US");
//
//            System.out.println(catalogOffer);

            gameSaveService.startEpicGames();

        };
    }

}
