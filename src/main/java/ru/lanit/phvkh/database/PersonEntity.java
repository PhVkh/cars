package ru.lanit.phvkh.database;

import ru.lanit.phvkh.dto.PersonDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "people")
public class PersonEntity {
    @Id
    @Column(name = "id")
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    private String name;
    public String getName() {
        return name;
    }

    @Column(name = "date_of_birth")
    private LocalDate date;
    public LocalDate getDate() {
        return date;
    }

    @OneToMany(mappedBy = "owner")
    private Set<CarEntity> cars;
    public Set<CarEntity> getCars() {
        return cars;
    }
    public void setCars(Set<CarEntity> cars) {
        this.cars = cars;
    }

    public PersonEntity(PersonDTO person) {
        this.id = person.getId();
        this.name = person.getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("d.MM.yyyy"));
        LocalDate dateOfBirth = LocalDate.parse(person.getDateOfBirth(), formatter);
        this.date = dateOfBirth;
    }

    public PersonEntity() {
    }
}