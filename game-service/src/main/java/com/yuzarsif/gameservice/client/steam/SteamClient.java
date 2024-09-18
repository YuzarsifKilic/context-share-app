package com.yuzarsif.gameservice.client.steam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yuzarsif.gameservice.client.steam.response.AppDetailsResponse;
import com.yuzarsif.gameservice.client.steam.response.AppListResponse;
import com.yuzarsif.gameservice.utils.PcRequirementsDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SteamClient {

    private final RestTemplate restTemplate;

    public SteamClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AppListResponse getApps() {
        ResponseEntity<AppListResponse> response = restTemplate.exchange(
                "http://api.steampowered.com/ISteamApps/GetAppList/v2",
                org.springframework.http.HttpMethod.GET,
                new HttpEntity<>(null),
                AppListResponse.class
        );

        return response.getBody();
    }

    public AppDetailsResponse getAppDetails(String appId) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity("https://store.steampowered.com/api/appdetails?appids=" + appId, String.class);

        String jsonString = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(AppDetailsResponse.PcRequirements.class, new PcRequirementsDeserializer());
        objectMapper.registerModule(simpleModule);


        try {
            Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Map<String, Object> valueMap = (Map<String, Object>) entry.getValue();

                AppDetailsResponse gameDetails = objectMapper.convertValue(valueMap, AppDetailsResponse.class);
                if (Boolean.TRUE.equals(gameDetails.getSuccess())) {
                    AppDetailsResponse convertedGameDetails = clearData(gameDetails);
                    return convertedGameDetails;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean checkValidResponse(AppDetailsResponse appDetailsResponse) {
        return appDetailsResponse.getData() != null && appDetailsResponse.getSuccess() != null
                && appDetailsResponse.getData().getSupported_languages() != null && appDetailsResponse.getData().getAbout_the_game() != null
                && appDetailsResponse.getData().getShort_description() != null && appDetailsResponse.getData().getDetailed_description() != null
                && appDetailsResponse.getData().getDevelopers() != null && appDetailsResponse.getData().getPrice_overview() != null
                && appDetailsResponse.getData().getRelease_date() != null && appDetailsResponse.getData().getPublishers() != null
                && appDetailsResponse.getData().getPc_requirements() != null;
    }

    private AppDetailsResponse clearData(AppDetailsResponse appDetailsResponse) {
        List<String> invalidKeys = List.of("<h1>", "</h1>", "<p>", "</p>", "<br>", "<ul>", "</ul>", "<li>", "</li>", "<strong>", "</strong>", "<a>", "</a>", "<h2>", "</h2>", "<i>", "</i>", "<ul class=\"bb_ul\">", "<br />", "<h2 class=\"bb_tag\">");

        String regex = "<img[^>]*?/>";

        for (String invalidKey : invalidKeys) {
            appDetailsResponse.getData().setShort_description(appDetailsResponse.getData().getShort_description().replace(invalidKey, ""));
            appDetailsResponse.getData().setAbout_the_game(appDetailsResponse.getData().getAbout_the_game().replace(invalidKey, ""));
            appDetailsResponse.getData().setDetailed_description(appDetailsResponse.getData().getDetailed_description().replace(invalidKey, ""));
        }

        appDetailsResponse.getData().setShort_description(appDetailsResponse.getData().getShort_description().replaceAll(regex, ""));
        appDetailsResponse.getData().setAbout_the_game(appDetailsResponse.getData().getAbout_the_game().replaceAll(regex, ""));
        appDetailsResponse.getData().setDetailed_description(appDetailsResponse.getData().getDetailed_description().replaceAll(regex, ""));
        return appDetailsResponse;
    }
}
