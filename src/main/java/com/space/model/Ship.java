package com.space.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Domain object what represent ship.
 */
@Entity
@Table(name = "ship", schema = "cosmoport")
@Access(AccessType.FIELD)
public class Ship extends AbstractBaseEntity {

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "planet", length = 50)
    private String planet;

    @Column(name = "shipType")
    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    @Column(name = "prodDate")
    @Temporal(TemporalType.DATE)
    private Date prodDate;

    @Column(name = "isUsed")
    private Boolean isUsed;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "crewSize")
    private Integer crewSize;

    @Column(name = "rating")
    private Double rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double sped) {
        this.speed = sped;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Ship() {
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planet='" + planet + '\'' +
                ", shipType=" + shipType +
                ", prodDate=" + prodDate +
                ", isUsed=" + isUsed +
                ", sped=" + speed +
                ", crewSize=" + crewSize +
                ", rating=" + rating +
                '}';
    }
}
