package ru.lanit.phvkh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.lanit.phvkh.database.CarEntity;

public class CarDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("model")
    private String model;
    @JsonProperty("horsepower")
    private Integer horsepower;
    @JsonProperty("ownerId")
    private Long ownerId;

    public CarDTO() {
    }

    public CarDTO(CarEntity car) {
        this.id = car.getId();
        this.model = car.getVendor() + "-" + car.getModel();
        this.horsepower = car.getHorsepower();
        this.ownerId = car.getOwner().getId();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public Integer getHorsepower() {
        return horsepower;
    }
    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
