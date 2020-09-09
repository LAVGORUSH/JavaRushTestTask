package com.space.utils;

import java.util.Date;

public class DateUtil {
    private DateUtil() {
    }

    public static Date parse(Long dateInSeconds) {
        return new Date(dateInSeconds * 1000);
    }
}
