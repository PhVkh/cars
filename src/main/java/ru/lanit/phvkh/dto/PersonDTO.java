package ru.lanit.phvkh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.lanit.phvkh.database.PersonEntity;

import java.util.Set;

public class PersonDTO {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Date of birth")
    private String dateOfBirth;
    @JsonProperty("Cars")
    private Set<CarDTO> cars;

    public PersonDTO() {
    }

    public PersonDTO(PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.name = personEntity.getName();
        this.dateOfBirth = personEntity.getDate().toString();
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
