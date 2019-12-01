package com.clics.compliancereport.service;

import com.clics.compliancereport.common.Constants;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBInfo;
import com.clics.compliancereport.domain.DBProcessType;
import com.clics.compliancereport.exception.ServiceException;
import com.clics.compliancereport.repository.DBInfoRepository;
import com.clics.compliancereport.service.impl.CompReportServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompReportServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(CompReportServiceImplTest.class);

    @Mock
    private DBInfoRepository dbInfoRepositoryMock;
    @Mock
    private CompRequestService compRequestServiceMock;

    private CompReportService compReportService;

    @Before
    public void onSetup(){
        compReportService = new CompReportServiceImpl();
        dbInfoRepositoryMock = mock(DBInfoRepository.class);
        ReflectionTestUtils.setField(compReportService, "dbInfoRepository", dbInfoRepositoryMock);
        ReflectionTestUtils.setField(compReportService, "compRequestService", compRequestServiceMock);
    }


    @Test
    public void testFindByName() {
        List<DBInfo> dbInfos = TestUtil.getTestDBInfo();
        when(dbInfoRepositoryMock.findByStatusNotAndName(Constants.RETIRED, TestConstants.DB_TEST_NAME.toUpperCase())).thenReturn(dbInfos);
        List<DBInfo> list = compReportService.findByName(TestConstants.DB_TEST_NAME);
        assertThat(list, is(notNullValue()));
        assertThat("list should always be greater than 0", list.size() == 1);
    }

    @Test
    public void testFindByNameLike() {
        List<DBInfo> dbInfos = TestUtil.getTestDBInfoListLike();
        when(dbInfoRepositoryMock.findByStatusNotAndNameLikeOrderByNameAsc(Constants.RETIRED,
                TestConstants.DB_TEST_LIKE_NAME.toUpperCase() + "%")).thenReturn(dbInfos);
        List<DBInfo> list = compReportService.findByNameLike(TestConstants.DB_TEST_LIKE_NAME);
        assertThat(list, is(notNullValue()));
        assertThat("list size should be equal to 1", list.size() == 1);
    }


    @Test
    public void testProcessRequestSingleDatabaseRequest() {
        //given
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.SINGLE_DB_NAME);
        Boolean bSuccess = Boolean.TRUE;
        DBInfo dbInfo = TestUtil.getTestDBInfo().get(0);
        //when
        when(dbInfoRepositoryMock.findOne(TestConstants.DB_TEST_ID)).thenReturn(dbInfo);
        //then
        Boolean returnBool = compReportService.processRequest(compRequest);
        assertThat("match expected", bSuccess == returnBool);
    }

    @Test(expected = ServiceException.class)
    public void testProcessRequestSingleDatabaseRequestWithMissingId() {
        //given
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.SINGLE_DB_NAME_WITH_MISSING_ID);
        Boolean bSuccess = Boolean.TRUE;
        DBInfo dbInfo = TestUtil.getTestDBInfo().get(0);
        //when
        when(dbInfoRepositoryMock.findOne(TestConstants.DB_TEST_ID)).thenReturn(dbInfo);
        //then
        Boolean returnBool = compReportService.processRequest(compRequest);
        assertThat("match expected", bSuccess == returnBool);
    }

    @Test(expected = NullPointerException.class)
    public void testProcessRequestSingleDatabaseRequestNullPointerException() {
        //given
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.SINGLE_DB_NAME);
        Boolean bSuccess = Boolean.TRUE;
        DBInfo dbInfo = TestUtil.getTestDBInfo().get(0);
        //when
        when(dbInfoRepositoryMock.findOne(TestConstants.DB_TEST_ID)).thenReturn(dbInfo);
        //then
        Boolean returnBool = compReportService.processRequest(null);
        assertThat("match expected", bSuccess == returnBool);
    }

    @Test(expected = ServiceException.class)
    public void testProcessRequestSingleDatabaseRequestServiceException() {
        //given
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.SINGLE_DB_NAME);
        Boolean bSuccess = Boolean.TRUE;
        DBInfo dbInfo = TestUtil.getTestDBInfo().get(0);
        //when
        when(dbInfoRepositoryMock.findOne(TestConstants.DB_TEST_ID)).thenThrow(PersistenceException.class);
        //then
        Boolean returnBool = compReportService.processRequest(compRequest);
        assertThat("match expected", bSuccess == returnBool);
    }


    @Test
    public void testProcessRequestMultipleDatabaseRequest() {
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.MULTIPLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.MULTIPLE_DB_NAMES);
        Boolean bSuccess = Boolean.TRUE;
        DBInfo dbInfo = TestUtil.getTestDBInfo().get(0);
        //when
        when(dbInfoRepositoryMock.findOne(TestConstants.DB_TEST_ID)).thenReturn(dbInfo);
        //then
        Boolean returnBool = compReportService.processRequest(compRequest);
        assertThat("match expected", bSuccess == returnBool);
    }


    @Test(expected = ServiceException.class)
    public void testProcessRequestMultipleDatabaseRequestBadProcessType() {
        CompRequest compRequest = TestUtil.testCompRequestData(null);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.MULTIPLE_DB_NAMES);
        Boolean bSuccess = Boolean.TRUE;
        DBInfo dbInfo = TestUtil.getTestDBInfo().get(0);
        //when
        when(dbInfoRepositoryMock.findOne(TestConstants.DB_TEST_ID)).thenReturn(dbInfo);
        //then
        Boolean returnBool = compReportService.processRequest(compRequest);
        assertThat("match expected", bSuccess == returnBool);
    }


    @Test
    public void testProcessRequestAllDatabaseRequest() {
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.ALL);
        Boolean bSuccess = Boolean.TRUE;
        DBInfo dbInfo = TestUtil.getTestDBInfo().get(0);
        //when
        when(dbInfoRepositoryMock.findOne(TestConstants.DB_TEST_ID)).thenReturn(dbInfo);
        //then
        Boolean returnBool = compReportService.processRequest(compRequest);
        assertThat("match expected", bSuccess == returnBool);
    }



}
