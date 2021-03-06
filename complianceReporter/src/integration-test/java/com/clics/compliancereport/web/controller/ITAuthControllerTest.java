package com.clics.compliancereport.web.controller;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ITAuthControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(ITAuthControllerTest.class);

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AuthController()).build();
    }

    @After
    public void tearDown() throws Exception {
        mockMvc = null;
    }

    @Test
    public void testAuthError() throws Exception {
        this.mockMvc.perform(get("/error")).andExpect(status().isOk()).andExpect(view().name("autherror"));
    }

    @Test
    public void testLogout() throws Exception {
        //logout will be redirected to Siteminder logout so status will be moved Temporarily (http 302)
        this.mockMvc.perform(get("/logout"))
                .andExpect(status()
                .isMovedTemporarily())
                .andExpect(redirectedUrl(AuthController.SSO_LOGOUT_URL));
    }
}
