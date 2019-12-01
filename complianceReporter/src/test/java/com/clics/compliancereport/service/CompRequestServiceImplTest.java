package com.clics.compliancereport.service;

import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.CompRequest;
import com.clics.compliancereport.domain.DBProcessType;
import com.clics.compliancereport.exception.ServiceException;
import com.clics.compliancereport.repository.CompRequestRepository;
import com.clics.compliancereport.repository.RequestSearchRepository;
import com.clics.compliancereport.service.impl.CompRequestServiceImpl;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CompRequestServiceImplTest {

    private CompRequestService compRequestService;

    @Mock
    private CompRequestRepository compRequestRepositoryMock;

    @Mock
    private RequestSearchRepository requestSearchRepositoryMock;


    @Before
    public void onSetup(){
        compRequestService = new CompRequestServiceImpl();
        compRequestRepositoryMock = mock(CompRequestRepository.class);
        requestSearchRepositoryMock = mock(RequestSearchRepository.class);

        ReflectionTestUtils.setField(compRequestService, "compRequestRepository", compRequestRepositoryMock);
        ReflectionTestUtils.setField(compRequestService, "requestSearchRepository", requestSearchRepositoryMock);
    }


    @Test
    public void testSaveRequest() {
        //given
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.DB_TEST_NAME);
        compRequest.setUsage(TestConstants.DB_TEST_USAGE1);
        compRequest.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);
        //when
        when(compRequestRepositoryMock.save(compRequest)).thenReturn(compRequest);
        //when
        boolean bSuccess = compRequestService.saveRequest(compRequest);
        assertThat("success set to true is expected", bSuccess);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveRequestNullRequest() {
        //given
        CompRequest compRequest = null;
        //when
        when(compRequestRepositoryMock.save(compRequest)).thenReturn(compRequest);
        //when
        compRequestService.saveRequest(compRequest);
    }


    @Test(expected = ServiceException.class)
    public void testSaveRequestExpectServiceException() {
        //given
        CompRequest compRequest = TestUtil.testCompRequestData(DBProcessType.SINGLE);
        compRequest.setSsoId(TestConstants.TEST_SSO);
        compRequest.setEmailAddress(TestConstants.TEST_EMAIL);
        compRequest.setDatabaseName(TestConstants.DB_TEST_NAME);
        compRequest.setUsage(TestConstants.DB_TEST_USAGE1);
        compRequest.setVirtualSqlInstance(TestConstants.DB_TEST_VSI);
        //when
        when(compRequestRepositoryMock.save(compRequest)).thenThrow(PersistenceException.class);
        //when
        compRequestService.saveRequest(compRequest);
    }

    @Test
    public void testGetAllRequests() {
        //given
        List<CompRequest> compRequests = TestUtil.getTestRequests();
        //when
        when(compRequestRepositoryMock.getRequestsBySSOId(TestConstants.TEST_SSO)).thenReturn(compRequests);
        //then
        List<CompRequest> requests = compRequestService.getRequestsBySSOId(TestConstants.TEST_SSO);
        assertThat(requests, is(notNullValue()));
        assertThat("list should not be empty", !requests.isEmpty());
        assertThat("size of lists should be equal", requests.size() == compRequests.size());
    }

    @Test
    public void testSaveRequests() {
        //given
        List<CompRequest> compRequests = TestUtil.getTestRequests();
        //when
        when(compRequestRepositoryMock.save(compRequests)).thenReturn(compRequests);
        //then
        boolean bSuccess = compRequestService.saveRequests(compRequests);
        assertTrue("save not successful", bSuccess);
    }

    @Test(expected = ServiceException.class)
    public void testSaveRequestsExpectServiceException() {
        //given
        List<CompRequest> compRequests = TestUtil.getTestRequests();
        //when
        when(compRequestRepositoryMock.save(compRequests)).thenThrow(PersistenceException.class);
        //then
        boolean bSuccess = compRequestService.saveRequests(compRequests);
        assertTrue("save not successful", bSuccess);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSaveRequestsWithNullRequests() {
        //given
        List<CompRequest> compRequests = null;
        //when
        when(compRequestRepositoryMock.save(compRequests)).thenReturn(compRequests);
        //then
        compRequestService.saveRequests(compRequests);
    }


    @Test
    public void testFindRequestsWithDatatablesCriterias() {
        //given
        DatatablesCriterias datatablesCriterias = TestUtil.getSampleDatatablesCriterias();
        List<CompRequest> compRequests = TestUtil.getTestRequests();
        //when
        when(requestSearchRepositoryMock.findRequestsWithDatatablesCriterias(datatablesCriterias)).thenReturn(compRequests);
        //then
        DataSet<CompRequest> requestDataSet = compRequestService.findRequestsWithDatatablesCriterias(datatablesCriterias);
        assertThat(requestDataSet, is(notNullValue()));
        assertThat(requestDataSet.getRows(), is(notNullValue()));
        assertThat("list should not be empty", !requestDataSet.getRows().isEmpty());
    }


    @Test(expected = ServiceException.class)
    public void testFindRequestsWithDatatablesCriteriasExpectServiceException() {
        //given
        DatatablesCriterias datatablesCriterias = TestUtil.getSampleDatatablesCriterias();
        //when
        when(requestSearchRepositoryMock.findRequestsWithDatatablesCriterias(datatablesCriterias)).thenThrow(PersistenceException.class);
        //then
        DataSet<CompRequest> requestDataSet = compRequestService.findRequestsWithDatatablesCriterias(datatablesCriterias);
        assertThat(requestDataSet, is(notNullValue()));
        assertThat(requestDataSet.getRows(), is(notNullValue()));
        assertThat("list should not be empty", !requestDataSet.getRows().isEmpty());
    }










}
