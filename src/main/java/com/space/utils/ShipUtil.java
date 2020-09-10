package com.space.utils;

import com.space.model.Ship;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShipUtil {

    private ShipUtil() {
    }

    public static Double calculateRating(Ship ship) {
        Double v = ship.getSpeed();
        Double k = ship.getUsed() ? 0.5 : 1;
        Integer y0 = 3019 - 1900;
        Integer y1 = ship.getProdDate().getYear();
        return new BigDecimal(80 * v * k / (y0 - y1 + 1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static Ship transformDataForUpdate(Ship found, Ship ship) {
        return new Ship(found.getId(), StringUtils.isEmpty(ship.getName()) ? found.getName() : ship.getName(),
                ship.getPlanet() == null ? found.getPlanet() : ship.getPlanet(),
                ship.getShipType() == null ? found.getShipType() : ship.getShipType(),
                ship.getProdDate() == null ? found.getProdDate() : ship.getProdDate(),
                ship.getUsed() == null ? found.getUsed() : ship.getUsed(),
                ship.getSpeed() == null ? found.getSpeed() : ship.getSpeed(),
                ship.getCrewSize() == null ? found.getCrewSize() : ship.getCrewSize(), found.getRating());
    }
}
