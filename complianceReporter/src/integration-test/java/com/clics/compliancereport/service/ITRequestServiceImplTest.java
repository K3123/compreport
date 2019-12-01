package com.clics.compliancereport.service;

import com.clics.compliancereport.common.AbstractTestCase;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBProcessType;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@DatabaseSetup("/META-INF/comprequest-dataset-sql.xml")
public class ITRequestServiceImplTest extends AbstractTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(CompRequestServiceImplTest.class);

    @Autowired
    private CompRequestService requestService;

    private DatatablesCriterias datatablesCriterias;

    @Before
    public void onSetup(){
        datatablesCriterias = TestUtil.getSampleDatatablesCriterias();
    }


    @Test
    public void testFindRequestsWithDatatablesCriterias() {
        DataSet<CompRequest> requestDataSet = requestService.findRequestsWithDatatablesCriterias(datatablesCriterias);
        assertNotNull(requestDataSet);
        assertNotNull(requestDataSet.getRows());
        assertTrue("list should not be empty", !requestDataSet.getRows().isEmpty());
    }


    @Test
    public void testGetRequestsBySSOId() {
        List<CompRequest> compRequests = requestService.getRequestsBySSOId(TestConstants.TEST_SSO);
        assertThat(compRequests, is(notNullValue()));
        assertTrue("list should not be empty", !compRequests.isEmpty());
        assertEquals(compRequests.size(), 1);
    }


    @Test
    public void testSaveRequest() {
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.DB_TEST_NAME);
        compRequest.setUsage(TestConstants.DB_TEST_USAGE1);
        compRequest.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);

        boolean bSuccess = requestService.saveRequest(compRequest);
        assertTrue("save not successful", bSuccess == true);
        assertNotNull("Id should be present after save", compRequest.getId());
    }


    @Test
    public void testSaveRequests() {
        CompRequest compRequest1 = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest1.setSsoId(TestConstants.TEST_SSO);
        compRequest1.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest1.setDatabaseName(TestConstants.DB_TEST_NAME1);
        compRequest1.setUsage(TestConstants.DB_TEST_USAGE1);
        compRequest1.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);

        CompRequest compRequest2 = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest2.setSsoId(TestConstants.TEST_SSO);
        compRequest2.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest2.setDatabaseName(TestConstants.DB_TEST_NAME2);
        compRequest2.setUsage(TestConstants.DB_TEST_USAGE2);
        compRequest2.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);


        CompRequest compRequest3 = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest3.setSsoId(TestConstants.TEST_SSO);
        compRequest3.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest3.setDatabaseName(TestConstants.DB_TEST_NAME3);
        compRequest3.setUsage(TestConstants.DB_TEST_USAGE3);
        compRequest3.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);

        List<CompRequest> compRequests = new ArrayList<CompRequest>();
        compRequests.add(compRequest1);
        compRequests.add(compRequest2);
        compRequests.add(compRequest3);

        boolean bSuccess = requestService.saveRequests(compRequests);
        assertThat("save not successful", bSuccess == true);
        assertThat(compRequest1.getId(), is(notNullValue()));
        assertThat(compRequest2.getId(), is(notNullValue()));
        assertThat(compRequest3.getId(), is(notNullValue()));
    }
}
