package com.clics.compliancereport.web.controller;


import com.clics.compliancereport.bean.FormBean;
import com.clics.compliancereport.common.AbstractContextControllerTests;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.service.CompReportService;
import com.clics.compliancereport.validator.FormBeanValidator;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(SpringJUnit4ClassRunner.class)
public class ITMainControllerTest extends AbstractContextControllerTests {

    private MockMvc mockMvc;

    @Autowired
    FormBeanValidator formBeanValidator;

    @Autowired
    CompReportService compReportService;


    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");

        MainController mainController =  new MainController();
        mainController.setFormBeanValidator(this.formBeanValidator);
        mainController.setCompReportService(this.compReportService);

        this.mockMvc = standaloneSetup(mainController).setViewResolvers(viewResolver).build();
    }

    @After
    public void tearDown() throws Exception {
        mockMvc = null;
    }

    @Test
    public void testShowNormalUserRequestForm() throws Exception {
        this.mockMvc.perform(get("/requestform"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(MainController.MODEL_KEY))
                .andExpect(view().name(MainController.FORM_VIEW));
    }


    @Test
    @DatabaseSetup("/META-INF/comprequest-dataset-sql.xml")
    public void testSaveRequestFormPositive() throws Exception {
        FormBean formBean = new FormBean();

        this.mockMvc.perform(post("/saverequest")
                .param("name", TestConstants.SINGLE_DB_NAME)
                .param("usage", TestConstants.DB_TEST_USAGE1)
                .param("virtualSqlInstance", "n/a")
                .param("email", "")
                .param("reportType", CompRequest.DUS_REPORT)
                .param("ssoId", "")
                .sessionAttr(MainController.MODEL_KEY, formBean))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(MainController.FORM_VIEW));
    }


    @Test
    public void testSaveRequestForrmWith2Errors() throws Exception {
        FormBean formBean = new FormBean();

        this.mockMvc.perform(post("/saverequest")
                .param("name", TestConstants.SINGLE_DB_NAME)
                .param("usage", TestConstants.DB_TEST_USAGE1)
                .param("virtualSqlInstance", "n/a")
                .param("email", "")
                .param("reportType", CompRequest.DUS_REPORT)
                .param("ssoId", "")
                .sessionAttr(MainController.MODEL_KEY, formBean))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2))
                .andExpect(view().name(MainController.FORM_VIEW));
    }



    @Test
    public void testSaveRequestFormWithMissingCredentials() throws Exception {
        FormBean formBean = new FormBean();

        this.mockMvc.perform(post("/saverequest")
                .param("name", TestConstants.SINGLE_DB_NAME)
                .param("usage", TestConstants.DB_TEST_USAGE1)
                .param("virtualSqlInstance", "n/a")
                .param("email", "")
                .param("reportType", CompRequest.DUS_REPORT)
                .param("ssoId", "")
                .sessionAttr(MainController.MODEL_KEY, formBean))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors(MainController.MODEL_KEY, "ssoId", "email"))
                .andExpect(view().name(MainController.FORM_VIEW));
    }

    @Test
    @DatabaseSetup("/META-INF/dbinventory-dataset-sql.xml")
    public void testDatabaseSearch() throws Exception {
        FormBean formBean = new FormBean();

        this.mockMvc.perform(get("/databaseSearch").param("term", TestConstants.DB_TEST_NAME)
                .sessionAttr(MainController.MODEL_KEY, formBean))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.dbInfos[0].id").value(219))
                .andExpect(jsonPath("$.dbInfos[0].name").value("ACBSCONV"))
                .andExpect(jsonPath("$.dbInfos[0].usage").value("DEV"))
                .andExpect(jsonPath("$.dbInfos[0].location").value("alpharetta"))
                .andExpect(jsonPath("$.dbInfos[0].version").value("10.2.0.4.0"))
                .andExpect(jsonPath("$.dbInfos[0].desc").value("cfs bi test"))
                .andExpect(jsonPath("$.dbInfos[0].serverName").value("cmfalgapacbs01"))
                .andExpect(jsonPath("$.dbInfos[0].virtualSqlInstance").value("n/a"))
                .andExpect(jsonPath("$.dbInfos[0].status").value(TestConstants.STATUS_UNLOCK));
    }

}
