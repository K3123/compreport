package com.clics.compliancereport.validator;

import com.clics.compliancereport.bean.FormBean;
import com.clics.compliancereport.common.TestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class FormBeanValidatorTest {

    private FormBeanValidator formBeanValidator;


    @Before
    public void before() throws Exception {
        formBeanValidator = new FormBeanValidator();
    }

    @After
    public void after() throws Exception {
        formBeanValidator = null;
    }

    @Test
    public void testSupports() {
        assertTrue(formBeanValidator.supports(FormBean.class));
        assertFalse(formBeanValidator.supports(Object.class));
    }

    @Test
    public void testFormBeanWithManditoryFields() throws Exception {
        FormBean formBean = TestUtil.testFormBeanWithManditoryFields();
        Errors errors = new BeanPropertyBindingResult(formBean, "formBean");
        formBeanValidator.validate(formBean, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testFormBeanWithMalformEmail() throws Exception {
        FormBean formBean = TestUtil.testFormBeanWithMalformEmail();
        Errors errors = new BeanPropertyBindingResult(formBean, "formBean");
        formBeanValidator.validate(formBean, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testFormBeanWithAllDBFalseAndNameMissing() throws Exception {
        FormBean formBean = TestUtil.testFormBeanWithAllDBFalseAndNameMissing();
        Errors errors = new BeanPropertyBindingResult(formBean, "formBean");
        formBeanValidator.validate(formBean, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testValidate() throws Exception {
        FormBean formBean = TestUtil.testFormBeanWithBadSSOId();
        Errors errors = new BeanPropertyBindingResult(formBean, "formBean");
        formBeanValidator.validate(formBean, errors);
        assertTrue(errors.hasErrors());
    }


} 
