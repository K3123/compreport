package com.clics.compliancereport.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PingController {
    private static final Logger LOG = LoggerFactory.getLogger(PingController.class);
    public static final String SUCCESS = "SUCCESS";

    @RequestMapping({"/ping"})
    @ResponseBody
    public String ping() {
        LOG.debug("<<<<< Entering 'ping()' method...");
        return SUCCESS;
    }

}
