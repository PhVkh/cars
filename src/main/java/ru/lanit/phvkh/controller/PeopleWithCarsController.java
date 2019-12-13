package ru.lanit.phvkh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lanit.phvkh.dto.CarDTO;
import ru.lanit.phvkh.dto.PersonDTO;
import ru.lanit.phvkh.dto.Statistics;
import ru.lanit.phvkh.service.*;

@RestController
public class PeopleWithCarsController {
    @Autowired
    AddPersonService addPersonService;
    @Autowired
    AddCarService addCarService;
    @Autowired
    ClearService clearService;
    @Autowired
    PersonWithCarsService personWithCarsService;
    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public ResponseEntity addPerson(@RequestBody PersonDTO person) {
        Status status = addPersonService.addPerson(person);
        switch (status) {
            case BAD_REQUEST:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            default: OK:
                return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST)
    public ResponseEntity addCar(@RequestBody CarDTO car) {
        Status status = addCarService.addCar(car);
        switch (status) {
            case BAD_REQUEST:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            default: OK:
                return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/personwithcars", method = RequestMethod.GET)
    public ResponseEntity getPersonWithCars(@RequestParam String personid) throws JsonProcessingException {
        Status status = personWithCarsService.checkId(personid);
        switch (status) {
            case BAD_REQUEST:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            case NOT_FOUND:
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String result = new ObjectMapper().writeValueAsString(personWithCarsService.getPersonAndHisCars(Integer.parseInt(personid)));
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResponseEntity getStatistics() throws JsonProcessingException {
        Statistics statistics = statisticsService.getStatistics();
        String result = new ObjectMapper().writeValueAsString(statistics);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public ResponseEntity clear() {
        clearService.clear();
        return new ResponseEntity(HttpStatus.OK);
    }
}
