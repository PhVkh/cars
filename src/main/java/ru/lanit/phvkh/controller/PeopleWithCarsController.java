package ru.lanit.phvkh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lanit.phvkh.dto.CarDTO;
import ru.lanit.phvkh.dto.PersonDTO;
import ru.lanit.phvkh.dto.Statistics;
import ru.lanit.phvkh.service.*;

@RestController
public class PeopleWithCarsController {
    @Autowired
    PersonService personService;
    @Autowired
    CarService carService;
    @Autowired
    AllDataService allDataService;

    @RequestMapping(value = "/person", method = RequestMethod.POST)

    public ResponseEntity addPerson(@RequestBody PersonDTO person) {
        Status status = personService.addPerson(person);
        switch (status) {
            case TIME_TRAVELER:
            case WRONG_DATE_FORMAT:
            case ID_CONFLICT:
            case NULLABLE_FIELDS:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST)
    public ResponseEntity addCar(@RequestBody CarDTO car) {
        Status status = carService.addCar(car);
        switch (status) {
            case ID_CONFLICT:
            case WEAK_CAR:
            case WRONG_CAR_MODEL:
            case YOUNG_OWNER:
            case OWNER_NOT_FOUND:
            case NULLABLE_FIELDS:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            default: OK:
                return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/personwithcars", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ResponseEntity getPersonWithCars(@RequestParam String personid) throws JsonProcessingException {
        Status status = personService.checkId(personid);
        switch (status) {
            case NULLABLE_FIELDS:
            case WRONG_DATA_FORMAT:
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            case OWNER_NOT_FOUND:
                return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String result = new ObjectMapper().writeValueAsString(personService.getPersonAndHisCars(Integer.parseInt(personid)));
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ResponseEntity getStatistics() throws JsonProcessingException {
        Statistics statistics = allDataService.getStatistics();
        String result = new ObjectMapper().writeValueAsString(statistics);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public ResponseEntity clear() {
        allDataService.clear();
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/home")
    public String home() {
        return "redirect:/statistics";
    }
}
