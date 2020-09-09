package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for {@link Ship} class.
 */
public interface ShipRepository extends JpaRepository<Ship, Long> {
}
