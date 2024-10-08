package com.yuzarsif.contextshare.reviewservice.clients.user;

import com.yuzarsif.contextshare.reviewservice.clients.CustomErrorHandler;
import com.yuzarsif.contextshare.reviewservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserClient {

    private final RestTemplate restTemplate;
    @Value("${clients.user-service}")
    private String userServiceUrl;

    public Boolean checkUserExist(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<Boolean> requestEntity = new HttpEntity<>(headers);

        restTemplate.setErrorHandler(new CustomErrorHandler());
        ResponseEntity<Boolean> response = restTemplate.exchange(
                userServiceUrl + "/api/v1/users/check/" + userId,
                HttpMethod.GET,
                requestEntity,
                Boolean.class);
        return response.getBody();
    }

    public List<UserResponse> findUsersByIdList(List<String> ids) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<List<String>> requestEntity = new HttpEntity<>(ids, headers);

        restTemplate.setErrorHandler(new CustomErrorHandler());
        ResponseEntity<List<UserResponse>> response = restTemplate.exchange(
                userServiceUrl + "/api/v1/users/find-by-id-list",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<UserResponse>>() {
                });
        log.info("User Client response: " + response.getBody());
        return response.getBody();
    }

    public UserResponse findUserById(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<Boolean> requestEntity = new HttpEntity<>(headers);

        restTemplate.setErrorHandler(new CustomErrorHandler());
        ResponseEntity<UserResponse> response = restTemplate.exchange(
                userServiceUrl + "/api/v1/users/" + id,
                HttpMethod.GET,
                requestEntity,
                UserResponse.class);
        log.info("User Client response: " + response.getBody());
        return response.getBody();
    }
}
