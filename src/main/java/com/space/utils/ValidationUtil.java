package com.space.utils;

import com.space.model.Ship;
import org.springframework.util.StringUtils;

import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

public class ValidationUtil {
    public static boolean validateId(Long id) {
        return id != null && id != 0;
    }

    public static boolean validateShipDataForCreate(Ship ship) {
        return !StringUtils.isEmpty(ship.getName()) && ship.getName().length() <= 50
                && !StringUtils.isEmpty(ship.getPlanet()) && ship.getPlanet().length() <= 50
                && ship.getShipType() != null
                && ship.getProdDate() != null
                && ship.getProdDate().getTime() >= new Date(900, Calendar.JANUARY,1).getTime()
                && ship.getProdDate().getTime() <= new Date(1119, Calendar.FEBRUARY,31).getTime()
                && ship.getSpeed() != null && ship.getSpeed() >= 0.01 && ship.getSpeed() <= 0.99
                && ship.getCrewSize() != null && ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999;
    }

    public static boolean validateShipDataForUpdate(Ship ship) {
        return (ship.getName() == null || (ship.getName().length() <= 50 && ship.getName().length() > 0))
                && (ship.getPlanet() == null || ship.getPlanet().length() <= 50)
                && (ship.getProdDate() == null || ship.getProdDate().getTime() >= 0)
                && (ship.getSpeed() == null || (ship.getSpeed() >= 0.01 && ship.getSpeed() <= 0.99))
                && (ship.getCrewSize() == null || (ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999));
    }

    public static boolean checkEmptyBody(Ship ship) {
        return StringUtils.isEmpty(ship.getName())
                && StringUtils.isEmpty(ship.getPlanet())
                && ship.getShipType() == null
                && ship.getProdDate() == null
                && ship.getSpeed() == null
                && ship.getCrewSize() == null;
    }

    public static <T> T checkNullAndReturnDefault(T param, T defaultParam) {
        return param == null ? defaultParam : param;
    }
}
