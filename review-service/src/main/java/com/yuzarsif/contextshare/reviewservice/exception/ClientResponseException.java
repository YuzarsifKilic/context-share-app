package com.yuzarsif.contextshare.reviewservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientResponseException extends RuntimeException {
    public ClientResponseException(String message) {
        super(message);
    }
}
