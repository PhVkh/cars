package ru.lanit.phvkh.service;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.phvkh.database.CarEntity;
import ru.lanit.phvkh.database.PersonDAO;
import ru.lanit.phvkh.database.PersonEntity;
import ru.lanit.phvkh.dto.CarDTO;
import ru.lanit.phvkh.dto.PersonDTO;

import java.util.HashSet;
import java.util.Set;

@Service
public class PersonWithCarsService {
    @Autowired
    PersonDAO personDAO;

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
                return Status.BAD_REQUEST;
            }
            if (existsPerson(id)) {
                return Status.OK;
            } else {
                return Status.NOT_FOUND;
            }
        } catch (Exception e) {
            System.out.println("Id должно быть числом.");
            return Status.BAD_REQUEST;
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
