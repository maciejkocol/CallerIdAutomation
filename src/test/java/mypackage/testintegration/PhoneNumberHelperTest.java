package mypackage.testintegration;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@IntegrationTest
public class PhoneNumberHelperTest{
    private final String resourcePath = "classpath:data/interview-callerid-data.csv";
    private final String mockedDataBad = "data/mocked-interview-callerid-data-bad.csv";

    @InjectMocks
    private CallerIdService callerIdService;

    @Mock
    ResourceLoader resourceLoader;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertPhoneNumberAsValid() throws Exception {
        when(resourceLoader.getResource(resourcePath)).thenReturn(new ClassPathResource(mockedDataBad));
        callerIdService.init();
        assertTrue(callerIdService.contains("+19876543210"));
    }

    @Test
    public void testConvertPhoneNumberAsInvalid() throws Exception {
        when(resourceLoader.getResource(resourcePath)).thenReturn(new ClassPathResource(mockedDataBad));
        callerIdService.init();
        assertFalse(callerIdService.contains("+12345678910"));
    }

    @Test
    public void testConvertPhoneNumberAsEmpty() throws Exception {
        when(resourceLoader.getResource(resourcePath)).thenReturn(new ClassPathResource(mockedDataBad));
        callerIdService.init();
        assertFalse(callerIdService.contains(""));
    }

}