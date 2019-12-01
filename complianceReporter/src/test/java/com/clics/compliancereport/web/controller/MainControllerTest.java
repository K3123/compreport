package com.clics.compliancereport.web.controller;

import com.clics.compliancereport.bean.FormBean;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBInfo;
import com.clics.compliancereport.exception.ServiceException;
import com.clics.compliancereport.service.CompReportService;
import com.clics.compliancereport.util.MiscUtil;
import com.clics.compliancereport.validator.FormBeanValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {
    @Mock
    private CompReportService compReportServiceMock;
    @Mock
    private FormBeanValidator formBeanValidatorMock;
    @Mock
    BindingResult bindingResultMock;
    @Mock
    RedirectAttributes attributesMock;
    @InjectMocks
    private MainController mainController;

    private MockHttpServletRequest requestMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainController = new MainController();
        ReflectionTestUtils.setField(mainController, "compReportService", compReportServiceMock);
        ReflectionTestUtils.setField(mainController, "formBeanValidator", formBeanValidatorMock);
        requestMock = new MockHttpServletRequest();
    }


    @Test
    public void testCreateForm() {
        //given
        BindingAwareModelMap model = new BindingAwareModelMap();
        //then
        String view = mainController.createForm(model);
        verifyZeroInteractions(compReportServiceMock, formBeanValidatorMock);
        assertTrue("views not match", MainController.FORM_VIEW.equals(view));
        assertThat(model.asMap().get(MainController.MODEL_KEY), is(notNullValue()));
    }


    @Test
    public void testSaveRequestFormWithErrors() {
        FormBean formBean = TestUtil.createFormBean();

        when(bindingResultMock.hasErrors()).thenReturn(true);

        String expectedView = mainController.saveRequestForm(formBean, bindingResultMock, requestMock, attributesMock);

        verifyZeroInteractions(compReportServiceMock);

        assertEquals(expectedView, MainController.FORM_VIEW);
    }

    @Test
    public void testSaveRequestFormWithNoErrors() {
        FormBean formBean = TestUtil.createFormBean();
        CompRequest compRequest = MiscUtil.populateCompRequest(formBean);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setSsoId(TestConstants.TEST_SSO);

        when(bindingResultMock.hasErrors()).thenReturn(false);

        String expectedView = mainController.saveRequestForm(formBean, bindingResultMock, requestMock, attributesMock);

        assertEquals("redirect:"+MainController.FORM_VIEW, expectedView);

    }

    @Test
    public void testSaveRequestFormWithNoErrors2() {
        FormBean formBean = TestUtil.createFormBean();
        CompRequest compRequest = MiscUtil.populateCompRequest(formBean);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setSsoId(TestConstants.TEST_SSO);

        when(compReportServiceMock.processRequest(compRequest)).thenThrow(ServiceException.class);

        String expectedView = mainController.saveRequestForm(formBean, bindingResultMock, requestMock, attributesMock);

        assertEquals("redirect:"+MainController.FORM_VIEW, expectedView);

    }

    @Test
    public void testGetDatabases() {
        String term = TestConstants.DB_TEST_LIKE_NAME;
        List<DBInfo> expectedDBInfos = TestUtil.getTestDBInfoList();
        when(compReportServiceMock.findByNameLike(term)).thenReturn(expectedDBInfos);

        List<DBInfo> dbInfos = compReportServiceMock.findByNameLike(term);

        assertThat(dbInfos, is(notNullValue()));
        assertEquals(expectedDBInfos.size(), dbInfos.size());
    }


}
