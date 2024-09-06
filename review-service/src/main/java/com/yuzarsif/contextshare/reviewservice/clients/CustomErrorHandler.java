package com.yuzarsif.contextshare.reviewservice.clients;

import com.yuzarsif.contextshare.reviewservice.exception.ClientException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class CustomErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            if (response.getStatusCode().value() == 400) {
                throw new ClientException("BAD REQUEST: " + response.getStatusText());
            } else if (response.getStatusCode().value() == 403) {
                throw new ClientException("Forbidden (403) hatası: " + response.getStatusText());
            } else {
                throw new ClientException("Başka bir 4xx hatası: " + response.getStatusCode() + " - " + response.getStatusText());
            }
        }

        if (response.getStatusCode().is5xxServerError()) {
            throw new ClientException("Sunucu hatası: " + response.getStatusCode() + " - " + response.getStatusText());
        }

        throw new ClientException("Bilinmeyen hata: " + response.getStatusText());
    }
}
