package com.clics.compliancereport.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    public static final String ERROR_VIEW = "autherror";
    public static final String SSO_LOGOUT_URL = "https://ssosslogin.stage.corporate.ge.com/logoff/logoff.jsp?referrer=http://compreport.dev.comfin.ge.com/compreport";

    @RequestMapping(value = "/error")
    public String error() {
        LOG.debug("<<<<< Entering 'error()' method...");
        return ERROR_VIEW;
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        LOG.debug("<<<<< Entering 'logout()' method...");
        return "redirect:" + SSO_LOGOUT_URL;
    }
}
