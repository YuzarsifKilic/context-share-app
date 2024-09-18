package com.yuzarsif.gameservice.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "checked_games")
public class CheckedGame {

    @Id
    private String gameId;
    private String platform;
    private Boolean nameEmpty;
    private Boolean priceEmpty;
    private Boolean graphicsEmpty;
    private Boolean processorEmpty;
    private Boolean storageEmpty;
    private Boolean osEmpty;
    private Boolean memoryEmpty;
    private Boolean systemRequirementsEmpty;
    private Boolean developerEmpty;
    private Boolean releaseDateEmpty;
    private Boolean publisherEmpty;
    private Boolean genreEmpty;
    private Boolean featureEmpty;
    private Boolean platformEmpty;
    private Boolean descriptionEmpty;
    private Boolean mainImageEmpty;
    private Boolean audioLanguageEmpty;
    private Boolean subtitleLanguageEmpty;
}
