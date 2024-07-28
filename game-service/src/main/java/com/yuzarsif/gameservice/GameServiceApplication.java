package com.yuzarsif.gameservice;

import com.yuzarsif.gameservice.model.Language;
import com.yuzarsif.gameservice.repository.LanguageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class GameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(LanguageRepository languageRepository) {
        return args -> {
            Language language1 = Language
                    .builder()
                    .name("English")
                    .build();
            Language language2 = Language
                    .builder()
                    .name("Turkish")
                    .build();
            Language language3 = Language
                    .builder()
                    .name("French")
                    .build();
            Language language4 = Language
                    .builder()
                    .name("Dutch")
                    .build();

            languageRepository.saveAll(List.of(language1, language2, language3, language4));
        };
    }

}
