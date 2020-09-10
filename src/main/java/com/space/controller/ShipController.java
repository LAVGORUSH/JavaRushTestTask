package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import com.space.service.specification.ShipSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.space.utils.ShipUtil.transformDataForUpdate;
import static com.space.utils.ValidationUtil.*;

@RestController
@RequestMapping(value = ShipController.REST_SHIPS_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ShipController {

    public static final String REST_SHIPS_URL = "/rest/ships";
    private final ShipService service;

    @Autowired
    public ShipController(ShipService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ship> getAllShip(@RequestParam(required = false) String name, @RequestParam(required = false) String planet,
                                 @RequestParam(required = false) ShipType shipType, @RequestParam(required = false) Boolean isUsed,
                                 @RequestParam(required = false) Long after, @RequestParam(required = false) Long before,
                                 @RequestParam(required = false) Double minSpeed, @RequestParam(required = false) Double maxSpeed,
                                 @RequestParam(required = false) Integer minCrewSize, @RequestParam(required = false) Integer maxCrewSize,
                                 @RequestParam(required = false) Double minRating, @RequestParam(required = false) Double maxRating,
                                 @RequestParam(defaultValue = "ID") ShipOrder order,
                                 @RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "3") Integer pageSize) {
        return service.getAllFiltered(ShipSpecifications.combineSpecification(name, planet, shipType, isUsed,
                after, before, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating),
                PageRequest.of(pageNumber, pageSize, new Sort(Sort.Direction.ASC, order.getFieldName())));
    }

    @GetMapping("/count")
    public Long getCountFiltered(@RequestParam(required = false) String name, @RequestParam(required = false) String planet,
                                 @RequestParam(required = false) ShipType shipType, @RequestParam(required = false) Boolean isUsed,
                                 @RequestParam(required = false) Long after, @RequestParam(required = false) Long before,
                                 @RequestParam(required = false) Double minSpeed, @RequestParam(required = false) Double maxSpeed,
                                 @RequestParam(required = false) Integer minCrewSize, @RequestParam(required = false) Integer maxCrewSize,
                                 @RequestParam(required = false) Double minRating, @RequestParam(required = false) Double maxRating) {
        return service.getCountFiltered(ShipSpecifications.combineSpecification(name, planet, shipType, isUsed,
                after, before, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating));
    }

    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        if (ship == null || checkEmptyBody(ship) || !validateShipDataForCreate(ship)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship created = service.create(ship);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
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

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @DeleteMapping(value = "/{id}")
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
