package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {

    List<Ship> getAll(Pageable pageable);

    Ship create(Ship ship);

    void update(Ship ship, Long id);

    void delete(Ship ship);

    Ship get(Long id);

    List<Ship> getAllFiltered(Specification<Ship> filter, Pageable pageable);

    Long getCountFiltered(Specification<Ship> filter);
}
