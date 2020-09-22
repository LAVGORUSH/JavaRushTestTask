package com.space.service.specification;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.utils.DateUtil;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;

import static com.space.utils.ValidationUtil.checkNullAndReturnDefault;

public class ShipSpecifications {
    private ShipSpecifications() {
    }

    public static Specification<Ship> isBetweenDate(Long after, Long before) {
        Date afterDate = DateUtil.parse(after);
        Date beforeDate = DateUtil.parse(before);
        return (root, query, cb) -> (afterDate == null && beforeDate == null) ? null : cb.between(root.get("prodDate"),
                checkNullAndReturnDefault(afterDate, new Date(0, 1, 1)),
                checkNullAndReturnDefault(beforeDate, new Date(1300, 1, 1)));
    }

    public static Specification<Ship> isBetweenMinAndMaxSpeed(Double minSpeed, Double maxSpeed) {
        return (root, query, cb) -> cb.between(root.get("speed"),
                checkNullAndReturnDefault(minSpeed, 0.01),
                checkNullAndReturnDefault(maxSpeed, 0.99));
    }

    public static Specification<Ship> isBetweenMinAndMaxCrewSize(Integer minCrewSize, Integer maxCrewSize) {
        return (root, query, cb) -> cb.between(root.get("crewSize"),
                checkNullAndReturnDefault(minCrewSize, 1),
                checkNullAndReturnDefault(maxCrewSize, 9999));
    }

    public static Specification<Ship> isBetweenMinAndMaxRating(Double minRating, Double maxRating) {
        return (root, query, cb) -> cb.between(root.get("rating"),
                checkNullAndReturnDefault(minRating, 0.00),
                checkNullAndReturnDefault(maxRating, 100.00));
    }

    public static Specification<Ship> isLikeName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Ship> isLikePlanet(String planet) {
        return (root, query, cb) -> planet == null ? null : cb.like(root.get("planet"), "%" + planet + "%");
    }

    public static Specification<Ship> isShipType(ShipType shipType) {
        return (root, query, cb) -> shipType == null ? null : cb.equal(root.get("shipType"), shipType);
    }

    public static Specification<Ship> isUsed(Boolean isUsed) {
        return (root, query, cb) -> isUsed == null ? null : cb.equal(root.get("isUsed"), isUsed);
    }

    public static Specification<Ship> combineSpecification(String name, String planet,
                                                           ShipType shipType, Boolean isUsed,
                                                           Long after, Long before,
                                                           Double minSpeed, Double maxSpeed,
                                                           Integer minCrewSize, Integer maxCrewSize,
                                                           Double minRating, Double maxRating) {
        return Specification.where(isLikeName(name)
                .and(isLikePlanet(planet))
                .and(isShipType(shipType))
                .and(isUsed(isUsed))
                .and(isBetweenDate(after, before))
                .and(isBetweenMinAndMaxSpeed(minSpeed, maxSpeed))
                .and(isBetweenMinAndMaxCrewSize(minCrewSize, maxCrewSize))
                .and(isBetweenMinAndMaxRating(minRating, maxRating)));
    }
}
