package com.yuzarsif.contextshare.reviewservice.clients;

import com.yuzarsif.contextshare.reviewservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameClient {

    private final RestTemplate restTemplate;
    @Value("${clients.game-service}")
    private String gameServiceUrl;

    public Boolean checkGameExist(Long gameId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<Boolean> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    gameServiceUrl + "/api/v1/games/check/" + gameId,
                    HttpMethod.GET,
                    requestEntity,
                    Boolean.class);
            log.info("Game Client response: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            throw new ClientException("Game Service is not available or " + gameServiceUrl + "/api/v1/games/check" + gameId + " is not working");
        }
    }
}
