package ru.lanit.phvkh.database;

import ru.lanit.phvkh.dto.CarDTO;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class CarEntity {
    @Id
    @Column(name = "id")
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "vendor")
    private String vendor;
    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Column(name = "model")
    private String model;
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "horsepower")
    private int horsepower;
    public int getHorsepower() {
        return horsepower;
    }
    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    @ManyToOne(targetEntity = PersonEntity.class)
    private PersonEntity owner;
    public PersonEntity getOwner() {
        return owner;
    }
    public void setOwner(PersonEntity owner) {
        this.owner = owner;
    }

    public CarEntity() {
    }

    public CarEntity(CarDTO car) {
        this.id = car.getId();
        this.horsepower = car.getHorsepower();

        int separator = car.getModel().indexOf("-");
        this.vendor = car.getModel().substring(0, separator);
        this.model = car.getModel().substring(separator + 1);
    }
}
