package com.clics.compliancereport.common;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:config/spring/spring-persistence-test-context.xml",
                               "classpath:config/spring/spring-service-context.xml"})
public abstract class AbstractTestCase extends AbstractDBUnitTest {

}
