package mypackage.testintegration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbk.callerid.controller.CallerIdController;
import com.sbk.callerid.model.CallerId;
import com.sbk.callerid.service.ICallerIdService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@IntegrationTest
public class CallerIdControllerTest {

    private String name = "Test Guru";
    private String number = "+14239611337";
    private String context = "new";
    private MockMvc mockMvc;

    @InjectMocks
    private CallerIdController callerIdController;

    @Mock
    private ICallerIdService idStore;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(callerIdController).build();
    }

    @After
    public void after() throws Exception {
        mockMvc = null;
    }

    @Test
    public void testQueryCallerIdOk() throws Exception {
        List<CallerId> list = new ArrayList<>();
        CallerId record = new CallerId(name, number, context);
        list.add(record);
        when(idStore.contains(number)).thenReturn(true);
        when(idStore.get(number)).thenReturn(list);
        MockHttpServletRequestBuilder builder = get("/query").param("number", number);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isOk());
    }

    @Test
    public void testQueryCallerIdBadRequest() throws Exception {
        MockHttpServletRequestBuilder builder = get("/query").param("number", "ALPHA");
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testQueryCallerIdNotFound() throws Exception {
        List<CallerId> list = new ArrayList<>();
        CallerId record = new CallerId(name, number, context);
        list.add(record);
        when(idStore.contains(number)).thenReturn(true);
        when(idStore.get(number)).thenReturn(list);
        MockHttpServletRequestBuilder builder = get("/query").param("number", number+"0");
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCallerIdCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CallerId record = new CallerId(name, number, context);
        String request = mapper.writeValueAsString(record);
        when(idStore.add(any(CallerId.class))).thenReturn(true);
        MockHttpServletRequestBuilder builder = post("/number").contentType(MediaType.APPLICATION_JSON)
                .content(request);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isCreated());
    }

    @Test
    public void testCreateCallerIdBadRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CallerId record = new CallerId(name, "ALPHA", context);
        String request = mapper.writeValueAsString(record);
        MockHttpServletRequestBuilder builder = post("/number").contentType(MediaType.APPLICATION_JSON)
                .content(request);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateCallerIdNotAcceptable() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CallerId record = new CallerId(name, number, context);
        String request = mapper.writeValueAsString(record);
        when(idStore.add(any(CallerId.class))).thenReturn(false);
        MockHttpServletRequestBuilder builder = post("/number").contentType(MediaType.APPLICATION_JSON)
                .content(request);
        ResultActions result = mockMvc.perform(builder);
        result.andExpect(status().isNotAcceptable());
    }
}