package ru.lanit.phvkh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.phvkh.database.CarDAO;
import ru.lanit.phvkh.database.PersonDAO;

@Service
public class ClearService {
    @Autowired
    PersonDAO personDAO;
    @Autowired
    CarDAO carDAO;

    @Transactional
    public void clear() {
        carDAO.clear();
        personDAO.clear();
    }
}
