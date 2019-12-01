package com.clics.compliancereport.util;


import com.clics.compliancereport.bean.FormBean;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBProcessType;
import org.junit.Test;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MiscUtilTest {



    @Test
    public void testPopulateCompRequest(){
        FormBean formBean = TestUtil.createFormBean();
        CompRequest built = MiscUtil.populateCompRequest(formBean);
        assertThat(built, is(notNullValue()));
        assertNull(built.getUsage());
        assertNull(built.getVirtualSqlInstance());
        assertEquals(built.getDatabaseName(), TestConstants.DB_TEST_NAME1);
        assertTrue(built.getDatabaseNames().isEmpty());
        assertEquals(built.getEmailAddress(), TestConstants.TEST_EMAIL);
        assertNull(built.getId());
        assertEquals(built.getComReportType(), CompRequest.DUS_REPORT);
        assertEquals(built.getInsertedBy(), CompRequest.INSERTED_BY);
        assertEquals(built.getUpdatedBy(), CompRequest.UPDATED_BY);
        assertNotNull(built.getReportDateStr());
        assertEquals(built.getReportType(), CompRequest.REPORT_TYPE);
        assertEquals(built.getRequestStatus(), CompRequest.REQUEST_STATUS);
        assertEquals(built.getSsoId(), TestConstants.TEST_SSO);
        assertNull(built.getReportDate());
        assertNotNull(built.getDbProcessType());
        assertEquals(built.getDbProcessType(), DBProcessType.SINGLE);
    }

    @Test
    public void testPopulateCompRequestMultiple(){
        FormBean formBean = TestUtil.createFormBeanMultiple();
        CompRequest built = MiscUtil.populateCompRequest(formBean);
        assertThat(built, is(notNullValue()));
        assertNull(built.getUsage());
        assertNull(built.getVirtualSqlInstance());
        assertEquals(built.getDatabaseName(), TestConstants.MULTIPLE_DB_NAMES);
        assertTrue(built.getDatabaseNames().isEmpty());
        assertEquals(built.getEmailAddress(), TestConstants.TEST_EMAIL);
        assertNull(built.getId());
        assertEquals(built.getComReportType(), CompRequest.DUS_REPORT);
        assertEquals(built.getInsertedBy(), CompRequest.INSERTED_BY);
        assertEquals(built.getUpdatedBy(), CompRequest.UPDATED_BY);
        assertNotNull(built.getReportDateStr());
        assertEquals(built.getReportType(), CompRequest.REPORT_TYPE);
        assertEquals(built.getRequestStatus(), CompRequest.REQUEST_STATUS);
        assertEquals(built.getSsoId(), TestConstants.TEST_SSO);
        assertNull(built.getReportDate());
        assertNotNull(built.getDbProcessType());
        assertEquals(built.getDbProcessType(), DBProcessType.MULTIPLE);
    }


    @Test
    public void testPopulateCompRequestAllDB(){
        FormBean formBean = TestUtil.createFormBeanAllDB();
        CompRequest built = MiscUtil.populateCompRequest(formBean);
        assertThat(built, is(notNullValue()));
        assertNull(built.getUsage());
        assertNull(built.getVirtualSqlInstance());
        assertNull(built.getDatabaseName());
        assertTrue(built.getDatabaseNames().isEmpty());
        assertEquals(built.getEmailAddress(), TestConstants.TEST_EMAIL);
        assertNull(built.getId());
        assertEquals(built.getComReportType(), CompRequest.DUS_REPORT);
        assertEquals(built.getInsertedBy(), CompRequest.INSERTED_BY);
        assertEquals(built.getUpdatedBy(), CompRequest.UPDATED_BY);
        assertNotNull(built.getReportDateStr());
        assertEquals(built.getReportType(), CompRequest.REPORT_TYPE);
        assertEquals(built.getRequestStatus(), CompRequest.REQUEST_STATUS);
        assertEquals(built.getSsoId(), TestConstants.TEST_SSO);
        assertNull(built.getReportDate());
        assertNotNull(built.getDbProcessType());
        assertEquals(built.getDbProcessType(), DBProcessType.ALL);
    }

    @Test
    public void testPopulateCompRequestNone(){
        FormBean formBean = TestUtil.createFormBeanNone();
        CompRequest built = MiscUtil.populateCompRequest(formBean);
        assertThat(built, is(notNullValue()));
        assertNull(built.getUsage());
        assertNull(built.getVirtualSqlInstance());
        assertNull(built.getDatabaseName());
        assertEquals(built.getEmailAddress(), TestConstants.TEST_EMAIL);
        assertNull(built.getId());
        assertEquals(built.getComReportType(), CompRequest.DUS_REPORT);
        assertEquals(built.getInsertedBy(), CompRequest.INSERTED_BY);
        assertEquals(built.getUpdatedBy(), CompRequest.UPDATED_BY);
        assertNotNull(built.getReportDateStr());
        assertEquals(built.getReportType(), CompRequest.REPORT_TYPE);
        assertEquals(built.getRequestStatus(), CompRequest.REQUEST_STATUS);
        assertEquals(built.getSsoId(), TestConstants.TEST_SSO);
        assertNull(built.getReportDate());
        assertNotNull(built.getDbProcessType());
        assertEquals(built.getDbProcessType(), DBProcessType.NONE);
    }
}
