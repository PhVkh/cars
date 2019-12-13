package ru.lanit.phvkh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.phvkh.database.CarEntity;
import ru.lanit.phvkh.database.PersonDAO;
import ru.lanit.phvkh.database.PersonEntity;
import ru.lanit.phvkh.dto.CarDTO;
import ru.lanit.phvkh.dto.PersonDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
public class PersonService {
    @Autowired
    PersonDAO personDAO;

    @Transactional
    public Status addPerson(PersonDTO person) {
        if (person.getId() != null && person.getName() != null && person.getDateOfBirth() != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("d.MM.yyyy"));
                LocalDate dateOfBirth = LocalDate.parse(person.getDateOfBirth(), formatter);
                LocalDate currentDate = LocalDate.now();
                if (dateOfBirth.isAfter(currentDate)) {
                    System.out.println("Пользователь должен быть рожден на данный момент.");
                    return Status.TIME_TRAVELER;
                }
            } catch (Exception e) {
                System.out.println("Неверный формат даты рождения.");
                return Status.WRONG_DATE_FORMAT;
            }
            if (personDAO.getPersonById(person.getId()) != null) {
                System.out.println("Пользователь с таким id уже существует.");
                return Status.ID_CONFLICT;
            }
            personDAO.addPerson(new PersonEntity(person));
            return Status.OK;
        } else {
            System.out.println("Поля не могут быть нулевыми.");
            return Status.NULLABLE_FIELDS;
        }
    }

    @Transactional
    public boolean existsPerson(long id) {
        PersonEntity personEntity = personDAO.getPersonById(id);
        if (personEntity == null) {
            System.out.println("Пользователя с таким id не существует.");
            return false;
        }
        return true;
    }

    @Transactional
    public Status checkId(String personId) {
        try {
            long id = Integer.parseInt(personId);
            if (personId == null) {
                System.out.println("Id не должно быть пусто пусто.");
                return Status.NULLABLE_FIELDS;
            }
            if (existsPerson(id)) {
                return Status.OK;
            } else {
                return Status.OWNER_NOT_FOUND;
            }
        } catch (Exception e) {
            System.out.println("Id должно быть числом.");
            return Status.WRONG_DATA_FORMAT;
        }
    }

    @Transactional
    public PersonDTO getPersonAndHisCars(long id) {
        PersonEntity personEntity = personDAO.getPersonById(id);
        Set<CarDTO> cars = new HashSet<>();
        for (CarEntity car : personEntity.getCars()) {
            cars.add(new CarDTO(car));
        }
        PersonDTO person = new PersonDTO(personEntity);
        person.setCars(cars);
        return person;
    }
}
