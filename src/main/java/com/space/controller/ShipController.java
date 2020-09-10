package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.space.utils.ShipUtil.transformDataForUpdate;
import static com.space.utils.ValidationUtil.*;

@RestController
@RequestMapping(value = "/rest/ships/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ShipController {

    private final ShipService service;

    @Autowired
    public ShipController(ShipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Ship>> getAllShip(@RequestParam String name, @RequestParam String planet,
                                                 @RequestParam ShipType shipType,
                                                 @RequestParam Long after, @RequestParam Long before,
                                                 @RequestParam Double minSpeed, @RequestParam Double maxSpeed,
                                                 @RequestParam Integer minCrewSize, @RequestParam Integer maxCrewSize,
                                                 @RequestParam Double minRating, @RequestParam Double maxRating,
                                                 @RequestParam ShipOrder order,
                                                 @RequestParam(defaultValue = "0") String pageNumber, @RequestParam(defaultValue = "3") String pageSize) {
        List<Ship> allFiltered = service.getAll(PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));

        if (allFiltered.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allFiltered, HttpStatus.OK);
    }

    @GetMapping("count")
    public ResponseEntity<Long> getCountFiltered(@RequestParam String name, @RequestParam String planet,
                                                 @RequestParam ShipType shipType,
                                                 @RequestParam Long after, @RequestParam Long before,
                                                 @RequestParam Double minSpeed, @RequestParam Double maxSpeed,
                                                 @RequestParam Integer minCrewSize, @RequestParam Integer maxCrewSize,
                                                 @RequestParam Double minRating, @RequestParam Double maxRating) {
        Long countFiltered = service.getCountFiltered(null);
        return new ResponseEntity<>(countFiltered, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        if (ship == null || checkEmptyBody(ship) || !validateShipDataForCreate(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship created = service.create(ship);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Long id) {
        if (!validateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = service.get(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @PostMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> updateShip(@RequestBody Ship ship, @PathVariable Long id) {
        if (ship == null || !validateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship found = service.get(id);
        if (found == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (checkEmptyBody(ship)) {
            return new ResponseEntity<>(found, HttpStatus.OK);
        }
        if (!validateShipDataForUpdate(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship updated = service.update(transformDataForUpdate(found, ship), id);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Ship> deleteShip(@PathVariable Long id) {
        if (!validateId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = service.get(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
