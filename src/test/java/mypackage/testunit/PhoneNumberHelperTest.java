package mypackage.testunit;

import com.sbk.callerid.service.PhoneNumberHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@UnitTest
public class PhoneNumberHelperTest{
    private final PhoneNumberHelper PNH_TEST = new PhoneNumberHelper();

    @Test
    public void testConvertPhoneNumberAsDefault() throws Exception {
        assertEquals(PNH_TEST.convertPhoneNumber(PNH_TEST.INVALID_INPUT), "-1");

    }

    @Test
    public void testConvertPhoneNumberAsEmpty() throws Exception {
        assertEquals(PNH_TEST.convertPhoneNumber(""), "-1");

    }

    @Test
    public void testConvertPhoneNumberAsInvalid() throws Exception {
        assertEquals(PNH_TEST.convertPhoneNumber("1-5"), "-1");

    }

    @Test
    public void testConvertPhoneNumberAsValid() throws Exception {
        assertEquals(PNH_TEST.convertPhoneNumber("(312)123-4567"), "+13121234567");

    }
}