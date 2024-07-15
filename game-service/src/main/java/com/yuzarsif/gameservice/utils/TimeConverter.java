package com.yuzarsif.gameservice.utils;

import java.time.LocalTime;

public class TimeConverter {

    public static LocalTime convert(String time) {
        return LocalTime.parse(time);
    }
}
