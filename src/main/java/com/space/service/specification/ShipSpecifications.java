package com.space.service.specification;

import com.space.model.Ship;
import com.space.utils.DateUtil;
import org.springframework.data.jpa.domain.Specification;

public class ShipSpecifications {
    private ShipSpecifications() {
    }

    public static Specification<Ship> isBetweenDate(Long after, Long before) {
        return (root, query, cb) -> cb.between(root.get("prodDate"), DateUtil.parse(before), DateUtil.parse(after));
    }

    public static Specification<Ship> isBetweenMinAndMaxSpeed(Double minSpeed, Double maxSpeed) {
        return (root, query, cb) -> cb.between(root.get("speed"), minSpeed, maxSpeed);
    }

    public static Specification<Ship> isBetweenMinAndMaxCrewSize(Integer minCrewSize, Integer maxCrewSize) {
        return (root, query, cb) -> cb.between(root.get("crewSize"), minCrewSize, maxCrewSize);
    }

    public static Specification<Ship> isBetweenMinAndMaxRating(Double minRating, Double maxRating) {
        return (root, query, cb) -> cb.between(root.get("rating"), minRating, maxRating);
    }

    public static Specification<Ship> combineSpecification(Long after, Long before,
                                                           Double minSpeed, Double maxSpeed,
                                                           Integer minCrewSize, Integer maxCrewSize,
                                                           Double minRating, Double maxRating) {
        return Specification.where(isBetweenDate(after, before)
                .and(isBetweenMinAndMaxSpeed(minSpeed, maxSpeed))
                .and(isBetweenMinAndMaxCrewSize(minCrewSize, maxCrewSize))
                .and(isBetweenMinAndMaxRating(minRating, maxRating)));
    }
}
