package ru.lanit.phvkh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.phvkh.database.PersonDAO;
import ru.lanit.phvkh.database.PersonEntity;
import ru.lanit.phvkh.dto.PersonDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AddPersonService {
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
                    return Status.BAD_REQUEST;
                }
            } catch (Exception e) {
                System.out.println("Неверный формат даты рождения.");
                return Status.BAD_REQUEST;
            }
            if (personDAO.getPersonById(person.getId()) != null) {
                System.out.println("Пользователь с таким id уже существует.");
                return Status.BAD_REQUEST;
            }
            personDAO.addPerson(new PersonEntity(person));
            return Status.OK;
        } else {
            System.out.println("Поля не могут быть нулевыми.");
            return Status.BAD_REQUEST;
        }
    }
}
