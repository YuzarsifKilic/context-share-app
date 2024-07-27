package com.yuzarsif.seriesservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DateFormatterException extends RuntimeException {

    public DateFormatterException(String message) {
        super(message);
    }

}
