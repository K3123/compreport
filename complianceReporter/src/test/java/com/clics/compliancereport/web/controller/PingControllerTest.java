package com.clics.compliancereport.web.controller;


import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PingControllerTest {
    private PingController controller;

    @Before
    public void setUp() {
        controller = new PingController();
    }

    @Test
    public void testPing() {
        String content = controller.ping();
        assertEquals(PingController.SUCCESS, content);
    }

}
