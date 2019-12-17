import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.lanit.phvkh.database.CarDAO;
import ru.lanit.phvkh.database.PersonDAO;
import ru.lanit.phvkh.service.AllDataService;
import ru.lanit.phvkh.service.PersonService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTests {
    @Mock
    PersonDAO personDAO;
    @Mock
    CarDAO carDAO;
    @InjectMocks
    AllDataService allDataService;

    @Test
    public void callingClearTest() {
        allDataService.clear();
        Mockito.verify(personDAO).clear();
        Mockito.verify(carDAO).clear();
    }

    @Test
    public void statisticsCallingTest() {
        allDataService.getStatistics();
        Mockito.when(personDAO.countPeople()).thenReturn(1L);
        Mockito.when(carDAO.countCars()).thenReturn(1L);
        Mockito.when(carDAO.countVendors()).thenReturn(1L);
        assertThat(personDAO.countPeople()).isEqualTo(1);
        assertThat(carDAO.countCars()).isEqualTo(1);
        assertThat(carDAO.countVendors()).isEqualTo(1);
    }

    @InjectMocks
    PersonService personService;

    @Test
    public void gettingPersonInExistsTest() {
        personService.existsPerson(1);
        Mockito.verify(personDAO).getPersonById(1);
    }


}
