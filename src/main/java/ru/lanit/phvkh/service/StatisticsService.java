package ru.lanit.phvkh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.phvkh.database.CarDAO;
import ru.lanit.phvkh.database.PersonDAO;
import ru.lanit.phvkh.dto.Statistics;

@Service
public class StatisticsService {
    @Autowired
    PersonDAO personDAO;
    @Autowired
    CarDAO carDAO;

    @Transactional
    public Statistics getStatistics() {
        long peopleNumber = personDAO.countPeople();
        long carNumber = carDAO.countCars();
        long vendorNumber = carDAO.countVendors();

        return new Statistics(peopleNumber, carNumber, vendorNumber);
    }
}
