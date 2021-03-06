package ru.lanit.phvkh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.phvkh.database.CarDAO;
import ru.lanit.phvkh.database.CarEntity;
import ru.lanit.phvkh.database.PersonDAO;
import ru.lanit.phvkh.database.PersonEntity;
import ru.lanit.phvkh.dto.CarDTO;

import java.time.LocalDate;

@Service
public class CarService {
    @Autowired
    CarDAO carDAO;
    @Autowired
    PersonDAO personDAO;

    @Transactional
    public Status addCar(CarDTO car) {
        if (car.getId() != null && car.getModel() != null && car.getHorsepower() != null && car.getOwnerId() != null) {
            if (carDAO.getCarById(car.getId()) != null) {
                System.out.println("Машина с таким id уже существует.");
                return Status.ID_CONFLICT;
            }
            if (car.getHorsepower() <= 0) {
                System.out.println("Маловато лошадиных сил.");
                return Status.WEAK_CAR;
            }
            int pos = car.getModel().indexOf("-");
            if (pos <= 0 || pos == car.getModel().length() - 1) {
                System.out.println("Ошибка в формате модели машины.");
                return Status.WRONG_CAR_MODEL;
            }
            PersonEntity owner = personDAO.getPersonById(car.getOwnerId());
            if (owner != null) {
                if (owner.getDate().isBefore(LocalDate.now().minusYears(18))) {
                    CarEntity carEntity = new CarEntity(car);
                    carEntity.setOwner(owner);
                    carDAO.addCar(carEntity);
                    return Status.OK;
                }
                System.out.println("владельцу машины нет 18-ти.");
                return Status.YOUNG_OWNER;
            } else {
                System.out.println("Владельца машины нет в базе.");
                return Status.OWNER_NOT_FOUND;
            }
        } else {
            return Status.NULLABLE_FIELDS;
        }
    }
}
