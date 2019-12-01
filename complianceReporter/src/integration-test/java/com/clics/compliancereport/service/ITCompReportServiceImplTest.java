package com.clics.compliancereport.service;

import com.clics.compliancereport.common.AbstractTestCase;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBInfo;
import com.clics.compliancereport.domain.DBProcessType;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@DatabaseSetup("/META-INF/dbinventory-dataset-sql.xml")
public class ITCompReportServiceImplTest extends AbstractTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(CompReportServiceImplTest.class);

    @Autowired
    private CompReportService compReportService;


    @Test
    public void testProcessRequestSingleDatabaseRequest() {
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.SINGLE_DB_NAME);
        Boolean bSuccess = compReportService.processRequest(compRequest);
        LOG.debug("CompRequest: " + compRequest);
        assertTrue("request process failed", bSuccess.booleanValue() == true);
    }


    @Test
    public void testProcessRequestMultipleDatabaseRequest() {
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.MULTIPLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.MULTIPLE_DB_NAMES);
        Boolean bSuccess = compReportService.processRequest(compRequest);
        assertTrue("request process failed", bSuccess.booleanValue() == true);
    }

    @Test
    public void testProcessRequestAllDatabaseRequest() {
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.ALL);
        Boolean bSuccess = compReportService.processRequest(compRequest);
        assertTrue("request process failed", bSuccess.booleanValue() == true);
    }


    @Test
    public void testFindByName() {
        List<DBInfo> list = compReportService.findByName(TestConstants.DB_TEST_NAME);
        assertThat(list, is(notNullValue()));
        assertEquals(list.size(),1);
    }

    @Test
    public void testFindByNameLike() {
        List<DBInfo> list = compReportService.findByNameLike(TestConstants.DB_TEST_LIKE_NAME);
        assertThat(list, is(notNullValue()));
        assertEquals(list.size(),  2);
    }

}
