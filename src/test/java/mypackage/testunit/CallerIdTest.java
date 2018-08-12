package mypackage.testunit;

import com.sbk.callerid.model.CallerId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@UnitTest
public class CallerIdTest {
    private String name = "Zelaya Flick";
    private String number = "1(908)587-3739";
    private String context = "twitter";
    private final CallerId CI_TEST = new CallerId(name, number, context);

    @Test
    public void testGetNumber() throws Exception {
        assertEquals(CI_TEST.getNumber(), number);
    }

    @Test
    public void testGetContext() throws Exception {
        assertEquals(CI_TEST.getContext(), context);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(CI_TEST.getName(), name);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals(CI_TEST.toString(), "CallerId{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", context='" + context + '\'' +
                '}');
    }
}