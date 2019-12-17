import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.lanit.phvkh.controller.PeopleWithCarsController;
import ru.lanit.phvkh.dto.PersonDTO;
import ru.lanit.phvkh.dto.Statistics;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class SpringContextTests {
    @Autowired
    PeopleWithCarsController peopleWithCarsController;

    @Test
    public void clearTest() throws JsonProcessingException {
        PersonDTO person = new PersonDTO();
        person.setId(-1L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        peopleWithCarsController.clear();
        peopleWithCarsController.addPerson(person);
        Statistics s = new ObjectMapper().readValue(peopleWithCarsController.getStatistics().getBody().toString(), Statistics.class);
        assertThat(s.getNumberOfPeople()).isEqualTo(1);
        peopleWithCarsController.clear();
        s = new ObjectMapper().readValue(peopleWithCarsController.getStatistics().getBody().toString(), Statistics.class);
        assertThat(s.getNumberOfPeople()).isEqualTo(0);
    }

    @Test
    public void addPersonTest() throws JsonProcessingException {
        PersonDTO person = new PersonDTO();
        person.setId(-1L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        peopleWithCarsController.clear();
        peopleWithCarsController.addPerson(person);
        PersonDTO personDTO = new ObjectMapper().readValue(peopleWithCarsController.getPersonWithCars("-1").getBody().toString(), PersonDTO.class);
        assertThat(personDTO.getName()).isEqualTo("Validperson1");
    }

    @Test
    public void addTwoPersonTest() throws JsonProcessingException {
        PersonDTO person = new PersonDTO();
        person.setId(-1L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        PersonDTO person2 = new PersonDTO();
        person2.setId(-2L);
        person2.setName("Validperson1");
        person2.setDateOfBirth("01.01.2000");

        peopleWithCarsController.clear();
        peopleWithCarsController.addPerson(person);
        peopleWithCarsController.addPerson(person2);
        Statistics s = new ObjectMapper().readValue(peopleWithCarsController.getStatistics().getBody().toString(), Statistics.class);
        assertThat(s.getNumberOfPeople()).isEqualTo(2);
    }
}
