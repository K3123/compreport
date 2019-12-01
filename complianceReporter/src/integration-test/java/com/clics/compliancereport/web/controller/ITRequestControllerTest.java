package com.clics.compliancereport.web.controller;


import com.clics.compliancereport.common.AbstractContextControllerTests;
import com.clics.compliancereport.common.Constants;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.UserCredential;
import com.clics.compliancereport.service.CompRequestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class ITRequestControllerTest extends AbstractContextControllerTests {
    private static final Logger LOG = LoggerFactory.getLogger(ITRequestControllerTest.class);

    private MockMvc mockMvc;

    @Autowired
    private CompRequestService compRequestService;

    @Before
    public void setUp() throws Exception {
        RequestController requestController = new RequestController();
        requestController.setCompRequestService(this.compRequestService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
    }

    @After
    public void tearDown() throws Exception {
        mockMvc = null;
    }


    @Test
    public void testShowRequestStatusPage() throws Exception {
        UserCredential credential = TestUtil.createUserCredential();
        mockMvc.perform(get("/requeststatus").requestAttr(Constants.CREDENTIAL_KEY, credential))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(RequestController.REQUEST_LIST_KEY))
                .andExpect(view().name(RequestController.REQUESTLIST_VIEW));
    }

    @Test
    public void testShowRequestStatusPageWithoutCredentials() throws Exception {
        mockMvc.perform(get("/requeststatus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(RequestController.ERROR_VIEW));
    }


    @Test
    public void testGetHeaders() throws Exception {
        mockMvc.perform(get("/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

}
