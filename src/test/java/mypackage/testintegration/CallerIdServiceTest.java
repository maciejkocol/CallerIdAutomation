package mypackage.testintegration;

import com.sbk.callerid.model.CallerId;
import com.sbk.callerid.service.CallerIdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@IntegrationTest
public class CallerIdServiceTest {

    private final String resourcePath = "classpath:data/interview-callerid-data.csv";
    private final String mockedData = "data/mocked-interview-callerid-data.csv";
    private final String mockedDataEmpty = "data/mocked-interview-callerid-data-empty.csv";
    private final String mockedDataBad = "data/mocked-interview-callerid-data-bad.csv";
    private final String mockedDataNull = "data/mocked-interview-callerid-data-null.csv";

    private final String name = "Test Guru";
    private final String number = "+12345678910";
    private final String context = "new";

    @InjectMocks
    private CallerIdService callerIdService;

    @Mock
    ResourceLoader resourceLoader;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInit() throws Exception {
        when(resourceLoader.getResource(resourcePath)).thenReturn(new ClassPathResource(mockedData));
        callerIdService.init();
        assertTrue(callerIdService.contains(number));
    }

    @Test
    public void testInitNoData() throws Exception {
        when(resourceLoader.getResource(resourcePath)).thenReturn(new ClassPathResource(mockedDataEmpty));
        callerIdService.init();
        assertFalse(callerIdService.contains(number));
    }

    @Test
    public void testInitBadData() throws Exception {
        when(resourceLoader.getResource(resourcePath)).thenReturn(new ClassPathResource(mockedDataBad));
        callerIdService.init();
        assertFalse(callerIdService.contains(number));
    }

    @Test
    public void testInitNullData() throws Exception {
        when(resourceLoader.getResource(resourcePath)).thenReturn(new ClassPathResource(mockedDataNull));
        callerIdService.init();
        assertFalse(callerIdService.contains(number));
    }

    @Test
    public void testAdd() throws Exception {
        callerIdService.add(new CallerId(name, number, context));
        assertTrue(callerIdService.contains(number));
    }

    @Test
    public void testAddExisting() throws Exception {
        callerIdService.add(new CallerId(name, number, context));
        assertFalse(callerIdService.add(new CallerId(name, number, context)));
    }

    @Test
    public void testAddEmpty() throws Exception {
        assertTrue(callerIdService.add(new CallerId("", "", "")));
    }

    @Test
    public void testGet() throws Exception {
        callerIdService.add(new CallerId(name, number, context));
        List<CallerId> result = callerIdService.get(number);
        assertEquals(result.toString(), "[" + new CallerId(name, number, context).toString() + "]");
    }

    @Test
    public void testGetNotFound() throws Exception {
        callerIdService.add(new CallerId(name, number, context));
        List<CallerId> result = callerIdService.get(number + "0");
        assertNotEquals(result.toString(), "[" + new CallerId(name, number, context).toString() + "]");
    }

}
