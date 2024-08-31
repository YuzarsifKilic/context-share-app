package com.yuzarsif.gameservice;

import com.yuzarsif.gameservice.client.SteamClient;
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
                                               PlatformRepository platformRepository) {
        return args -> {
            Optional<Platform> steam = platformRepository.findByName("STEAM");
            if (steam.isEmpty()) {
                Platform platform = Platform
                        .builder()
                        .name("STEAM")
                        .build();
                platformRepository.save(platform);
            }
            //gameSaveService.saveGamesBySteamClient();
        };
    }

}
