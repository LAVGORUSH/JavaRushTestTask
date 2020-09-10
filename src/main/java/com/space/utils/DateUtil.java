package com.space.utils;

import java.sql.Date;

public class DateUtil {

    private DateUtil() {
    }

    public static Date parse(Long dateInSeconds) {
        return dateInSeconds == null ? null : new Date(dateInSeconds);
    }
}
