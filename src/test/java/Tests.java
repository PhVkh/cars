import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.lanit.phvkh.dto.CarDTO;
import ru.lanit.phvkh.dto.PersonDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class Tests {
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/clear"));
    }

    @Test
    public void clearTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-1L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/clear"));
        mockMvc.perform(MockMvcRequestBuilders.get("/personwithcars").param("personid", "-1")).andExpect(status().isNotFound());
    }

    @Test
    public void addValidPersonTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-10L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/personwithcars").param("personid", "-10"))
                .andExpect(jsonPath("$.id").value("-10"))
                .andExpect(jsonPath("$.name").value("Validperson1"))
                .andExpect(jsonPath("$.birthdate").value("01.01.2000"))
                .andExpect(status().isOk());
    }

    @Test
    public void timeTravelerTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-39L);
        person.setName("valid");
        person.setDateOfBirth("01.01.2222");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void emptyIdTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(null);
        person.setName("valid");
        person.setDateOfBirth("01.01.2222");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void noIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("{\"name\":\"valid\",\"birthdate\":\"01.12.2017\"}"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void noNameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("{\"id\":\"14\",\"birthdate\":\"01.12.2017\"}"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void noBirthdateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString("{\"name\":\"valid\",\"id\":\"14\"}"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void wrongBirthdateFormatTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(null);
        person.setName("valid");
        person.setDateOfBirth("2222-01-25");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void nullPersonId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/personwithcars").param("personid", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void nonNumberPersonId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/personwithcars").param("personid", "xjg"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addValidCarTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-129L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        CarDTO car = new CarDTO();
        car.setId(-1300L);
        car.setModel("BMW-X5");
        car.setHorsepower(100);
        car.setOwnerId(-129L);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/personwithcars").param("personid", "-129"))
                .andExpect(jsonPath("$.cars.length()").value("1"))
                .andExpect(jsonPath("$.cars[0].model").value("BMW-X5"))
                .andExpect(jsonPath("$.cars[0].horsepower").value("100"))
                .andExpect(jsonPath("$.cars[0].ownerId").value("-129"))
                .andExpect(status().isOk());
    }

    @Test
    public void addTwoCarsTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-119L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        CarDTO car1 = new CarDTO();
        car1.setId(-131L);
        car1.setModel("BMW-X5");
        car1.setHorsepower(100);
        car1.setOwnerId(-119L);

        CarDTO car2 = new CarDTO();
        car2.setId(-132L);
        car2.setModel("Лада-Геннадий");
        car2.setHorsepower(70);
        car2.setOwnerId(-119L);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/personwithcars").param("personid", "-119"))
                .andExpect(jsonPath("$.cars.length()").value("2"))
                .andExpect(jsonPath("$.cars[?(@.horsepower < 100)].id").value(-132))
                .andExpect(jsonPath("$.cars[?(@.horsepower > 99)].id").value(-131))
                .andExpect(jsonPath("$.cars[?(@.horsepower < 100)].model").value("Лада-Геннадий"))
                .andExpect(jsonPath("$.cars[?(@.horsepower > 99)].model").value("BMW-X5"))
                .andExpect(jsonPath("$.cars[?(@.horsepower < 100)].ownerId").value(-119))
                .andExpect(jsonPath("$.cars[?(@.horsepower > 99)].ownerId").value(-119))
                .andExpect(status().isOk());
    }

    @Test
    public void carForAChildTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-128L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2015");

        CarDTO car = new CarDTO();
        car.setId(-10L);
        car.setModel("BMW-X5");
        car.setHorsepower(100);
        car.setOwnerId(-128L);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWeekCarTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-117L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        CarDTO car = new CarDTO();
        car.setId(-130L);
        car.setModel("BMW-X5");
        car.setHorsepower(0);
        car.setOwnerId(-117L);

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addEmptyNullModelCarTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-190L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"\",\"horsepower\":50,\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-189\",\"horsepower\":50,\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addEmptyNullIdCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"\",\"model\":\"\",\"horsepower\":50,\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"model\":\"\",\"horsepower\":50,\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addEmptyNullHorsepowerCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"\",\"horsepower\":\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"\",\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addEmptyNullOwnerCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"\",\"horsepower\":50,\"ownerId\":\"\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"\",\"horsepower\":50}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void wrongModelCarTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"LadaSedan\",\"horsepower\":50,\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"-LadaSedan\",\"horsepower\":50,\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-190\",\"model\":\"LadaSedan-\",\"horsepower\":50,\"ownerId\":\"-190\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sameIdCarTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-19L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        CarDTO car = new CarDTO();
        car.setId(-1302L);
        car.setModel("BMW-X5");
        car.setHorsepower(40);
        car.setOwnerId(-19L);

        CarDTO car2 = new CarDTO();
        car2.setId(-1302L);
        car2.setModel("BMW-X5");
        car2.setHorsepower(4);
        car2.setOwnerId(-19L);

        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sameIdPersonTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-1778L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        PersonDTO person2 = new PersonDTO();
        person2.setId(-1778L);
        person2.setName("Validperson1");
        person2.setDateOfBirth("01.01.2000");

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void statisticsTest() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setId(-167L);
        person.setName("Validperson1");
        person.setDateOfBirth("01.01.2000");

        PersonDTO person2 = new PersonDTO();
        person2.setId(-17L);
        person2.setName("Validperson1");
        person2.setDateOfBirth("01.01.2000");

        PersonDTO person3 = new PersonDTO();
        person3.setId(-112L);
        person3.setName("Validperson1");
        person3.setDateOfBirth("01.01.2000");

        CarDTO car = new CarDTO();
        car.setId(-130L);
        car.setModel("BMW-X5");
        car.setHorsepower(100);
        car.setOwnerId(-167L);

        CarDTO car2 = new CarDTO();
        car2.setId(-131L);
        car2.setModel("Лада-Геннадий");
        car2.setHorsepower(70);
        car2.setOwnerId(-112L);

        CarDTO car3 = new CarDTO();
        car3.setId(-132L);
        car3.setModel("ЛаДа-Геннадий");
        car3.setHorsepower(70);
        car3.setOwnerId(-112L);

        mockMvc.perform(MockMvcRequestBuilders.get("/clear"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/statistics"))
                .andExpect(jsonPath("$.personcount").value("0"))
                .andExpect(jsonPath("$.carcount").value("0"))
                .andExpect(jsonPath("$.uniquevendorcount").value("0"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person3))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/car")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(car3))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/statistics"))
                .andExpect(jsonPath("$.personcount").value("3"))
                .andExpect(jsonPath("$.carcount").value("3"))
                .andExpect(jsonPath("$.uniquevendorcount").value("2"))
                .andExpect(status().isOk());
    }
}
