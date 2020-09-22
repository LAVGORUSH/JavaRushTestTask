package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.space.utils.ShipUtil.calculateRating;
import static com.space.utils.ShipUtil.roundDouble;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipRepository repository;
    private final Logger log = LoggerFactory.getLogger(ShipServiceImpl.class);

    @Autowired
    public ShipServiceImpl(ShipRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ship create(Ship ship) {
        log.info("create new ship");
        ship.setUsed(ship.getUsed() != null && ship.getUsed());
        ship.setSpeed(roundDouble(ship.getSpeed()));
        ship.setRating(calculateRating(ship));
        return repository.save(ship);
    }

    @Override
    public Ship update(Ship ship, Long id) {
        log.info("update ship with id=" + id);
        ship.setSpeed(roundDouble(ship.getSpeed()));
        ship.setRating(calculateRating(ship));
        return repository.save(ship);
    }

    @Override
    public void delete(Long id) {
        log.info("delete ship with id=" + id);
        repository.deleteById(id);
    }

    @Override
    public Ship get(Long id) {
        log.info("get ship with id=" + id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Ship> getAllFiltered(Specification<Ship> filter, Pageable pageable) {
        log.info("get all filtered");
        return repository.findAll(filter, pageable).getContent();
    }

    @Override
    public Long getCountFiltered(Specification<Ship> filter) {
        log.info("get count filtered");
        return repository.count(filter);
    }
}
