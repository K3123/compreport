package com.clics.compliancereport.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;


@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-servlet.xml"})
public abstract class AbstractContextControllerTests extends AbstractTestCase {

    @Autowired
    protected WebApplicationContext webApplicationContext;

}
