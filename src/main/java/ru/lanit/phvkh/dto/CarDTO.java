package ru.lanit.phvkh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.lanit.phvkh.database.CarEntity;

public class CarDTO {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Model")
    private String model;
    @JsonProperty("Horsepower")
    private Integer horsepower;
    @JsonProperty("Owners Id")
    private Long ownerId;

    public CarDTO() {
    }

    public CarDTO(CarEntity car) {
        this.id = car.getId();
        this.model = car.getVendor() + " " + car.getModel();
        this.horsepower = car.getHorsepower();
        this.ownerId = car.getOwner().getId();
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
