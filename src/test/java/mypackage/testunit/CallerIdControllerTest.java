package mypackage.testunit;

import com.sbk.callerid.controller.CallerIdController;
import com.sbk.callerid.model.CallerId;
import com.sbk.callerid.service.ICallerIdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@UnitTest
public class CallerIdControllerTest {

    private String name = "Test Guru";
    private String number = "+14239611337";
    private String context = "new";

    @InjectMocks
    private CallerIdController callerIdController;

    @Mock
    private ICallerIdService idStore;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueryCallerIdOk() throws Exception {
        when(idStore.contains(number)).thenReturn(true);
        ResponseEntity response = callerIdController.queryCallerId(number);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testQueryCallerIdBadRequest() throws Exception {
        ResponseEntity response = callerIdController.queryCallerId("ALPHA");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testQueryCallerIdNotFound() throws Exception {
        ResponseEntity response = callerIdController.queryCallerId(number);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateCallerIdCreated() throws Exception {
        CallerId record = new CallerId(name, number, context);
        when(idStore.add(record)).thenReturn(true);
        ResponseEntity response = callerIdController.createCallerId(record);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testCreateCallerIdBadRequest() throws Exception {
        CallerId record = new CallerId(name, "ALPHA", context);
        ResponseEntity response = callerIdController.createCallerId(record);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCreateCallerIdNotAcceptable() throws Exception {
        CallerId record = new CallerId(name, number, context);
        ResponseEntity response = callerIdController.createCallerId(record);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }
}