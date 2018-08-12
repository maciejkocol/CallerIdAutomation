package mypackage.testintegration;

import com.sbk.callerid.model.CallerId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@IntegrationTest
public class CallerIdTest {
    private final CallerId CI_TEST = new CallerId("Zelaya Flick", "1(908)587-3739", "twitter");

    @Test
    public void testGetNumber() throws Exception {
        CI_TEST.setNumber("1(906)587-3739");
        assertEquals(CI_TEST.getNumber(), "1(906)587-3739");
    }

    @Test
    public void testSetNumber() throws Exception {
        CI_TEST.setNumber("1(907)587-3739");
        assertEquals(CI_TEST.getNumber(), "1(907)587-3739");
    }

    @Test
    public void testGetContext() throws Exception {
        CI_TEST.setContext("facebook");
        assertEquals(CI_TEST.getContext(), "facebook");
    }

    @Test
    public void testSetContext() throws Exception {
        CI_TEST.setContext("instagram");
        assertEquals(CI_TEST.getContext(), "instagram");
    }

    @Test
    public void testGetName() throws Exception {
        CI_TEST.setName("Zelaya Flicking");
        assertEquals(CI_TEST.getName(), "Zelaya Flicking");
    }

    @Test
    public void testSetName() throws Exception {
        CI_TEST.setName("Zelaya Flicker");
        assertEquals(CI_TEST.getName(), "Zelaya Flicker");
    }

    @Test
    public void testToString() throws Exception {
        CI_TEST.setName("Zelaya Smith");
        CI_TEST.setNumber("1(608)587-3739");
        CI_TEST.setContext("truly");
        Assert.assertEquals(CI_TEST.toString(), "CallerId{" +
                "name='" + CI_TEST.getName() + '\'' +
                ", number='" + CI_TEST.getNumber() + '\'' +
                ", context='" + CI_TEST.getContext() + '\'' +
                '}');
    }
}