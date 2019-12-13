package ru.lanit.phvkh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.lanit.phvkh.database.PersonEntity;

import java.time.format.DateTimeFormatter;
import java.util.Set;

public class PersonDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("birthdate")
    private String dateOfBirth;
    @JsonProperty("cars")
    private Set<CarDTO> cars;

    public PersonDTO() {
    }

    public PersonDTO(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.name = personEntity.getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.dateOfBirth = personEntity.getDate().format(formatter);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<CarDTO> getCars() {
        return cars;
    }
    public void setCars(Set<CarDTO> cars) {
        this.cars = cars;
    }
}
