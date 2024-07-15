package com.yuzarsif.movieservice.utils;

import com.yuzarsif.movieservice.exception.DateFormatterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public static Date convert(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new DateFormatterException("Date format is not valid: " + date);
        }
    }
}
