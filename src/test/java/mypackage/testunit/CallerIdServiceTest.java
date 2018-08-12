package mypackage.testunit;

import com.sbk.callerid.model.CallerId;
import com.sbk.callerid.service.CallerIdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@UnitTest
public class CallerIdServiceTest {

    private final String name = "Test Guru";
    private final String number = "+12345678910";
    private final String context = "new";

    @InjectMocks
    private CallerIdService callerIdService;

    @Mock
    private Map<String, Map<String, String>> store = new TreeMap<>();

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddNew() throws Exception {
        Boolean result = callerIdService.add(new CallerId(name, number, context));
        assertTrue(result);
    }

    @Test
    public void testAddExisting() throws Exception {
        Map<String, String> record = new TreeMap<>();
        Map<String, String> emptyRecord = new TreeMap<>();
        record.put(context, name);
        when(store.get(number)).thenReturn(record, emptyRecord);
        Boolean result = callerIdService.add(new CallerId(name, number, context));
        assertTrue(result);
    }

    @Test
    public void testAddContextExisting() throws Exception {
        Map<String, String> record = new TreeMap<>();
        record.put(context, name);
        when(store.get(number)).thenReturn(record);
        Boolean result = callerIdService.add(new CallerId(name, number, context));
        assertFalse(result);
    }

    @Test
    public void testGetFound() throws Exception {
        Map<String, String> record = new TreeMap<>();
        record.put(context, name);
        store.put(number, record);
        when(store.get(number)).thenReturn(record);
        List<CallerId> result = callerIdService.get(number);
        String callerId = "[CallerId{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", context='" + context + '\'' +
                "}]";
        assertEquals(result.toString(), callerId);
    }

    @Test
    public void testGetNotFound() throws Exception {
        when(store.get(number)).thenReturn(null);
        List<CallerId> result = new ArrayList<>();
        String callerId = "[]";
        assertEquals(result.toString(), callerId);
    }

    @Test
    public void testContains() throws Exception {
        when(store.containsKey(number)).thenReturn(true);
        Boolean result = callerIdService.contains(number);
        assertTrue(result);
    }

    @Test
    public void testContainsNot() throws Exception {
        when(store.containsKey(number)).thenReturn(false);
        Boolean result = callerIdService.contains(number);
        assertFalse(result);
    }
}