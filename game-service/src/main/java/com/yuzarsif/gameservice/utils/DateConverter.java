package com.yuzarsif.gameservice.utils;

import com.yuzarsif.gameservice.exception.DateFormatterException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateConverter {

    public static Date convert(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new DateFormatterException("Date format is not valid: " + date);
        }
    }

    public static Date steamDateToJavaDate(String date) {

        String[] check = date.split(",");

        String[] day = check[0].split(" ");

        if (day[0].length() == 1) {
            log.warn("Date format is not valid: " + date);
            day[0] = "0" + day[0];
            date = day[0] + " " + day[1] + "," + check[1];
        }

        String[] checkDateFormat = date.split(" ");
        SimpleDateFormat inputFormat = new SimpleDateFormat();
        if (checkDateFormat[0].length() == 3) {
            inputFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        } else if (checkDateFormat[0].length() == 2) {
            inputFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
        }
        try {
            return inputFormat.parse(date);
        } catch (ParseException e) {
            throw new DateFormatterException("Date format is not valid: " + date);
        }
    }
}
