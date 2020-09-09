package com.space.utils;

import com.space.model.Ship;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShipUtil {

    private ShipUtil() {
    }

    public static Double calculateRating(Ship ship) {
        Double v = ship.getSpeed();
        Double k = ship.getUsed() ? 0.5 : 1;
        Integer y0 = 3019 - 1970;
        Integer y1 = ship.getProdDate().getYear();
        return new BigDecimal(80 * v * k / (y0 - y1 + 1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
