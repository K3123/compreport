package com.clics.compliancereport.repository;


import com.clics.compliancereport.common.AbstractTestCase;
import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.CompRequest;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@DatabaseSetup("/META-INF/comprequest-dataset-sql.xml")
public class RequestSearchRepositoryTest extends AbstractTestCase {

    @Autowired
    RequestSearchRepository requestSearchRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void onSetup(){
        requestSearchRepository.setEntityManager(entityManager);
    }


    @Test
    public void testFindAll() {
        List<CompRequest> requestList = requestSearchRepository.findAll();
        assertThat(requestList, is(notNullValue()));
        assertThat("size is not what we expect", requestList.size() == 4);
    }


    @Test
    public void testFindRequestsWithDatatablesCriterias() {
        List<CompRequest> requestList = requestSearchRepository.findRequestsWithDatatablesCriterias(TestUtil.getSampleDatatablesCriterias());
        assertThat(requestList, is(notNullValue()));
        assertThat("size is not what we expect", requestList.size() == 1);
    }


    @Test
    public void testFindRequestsWithDatatablesCriterias2() {
        List<CompRequest> requestList = requestSearchRepository.findRequestsWithDatatablesCriterias(TestUtil.getSampleDatatablesCriterias2());
        assertThat(requestList, is(notNullValue()));
        assertThat("size is not what we expect", requestList.size() == 1);
    }

    @Test
    public void testFindRequestsWithDatatablesCriteriasNull() {
        List<CompRequest> requestList = requestSearchRepository.findRequestsWithDatatablesCriterias(null);
        assertThat(requestList, is(notNullValue()));
        assertThat("size is not what we expect", requestList.size() == 4);
    }


    @Test
    public void testGetFilteredCount() {
        Long filteredCount = requestSearchRepository.getFilteredCount(TestUtil.getSampleDatatablesCriterias());
        assertThat(filteredCount, is(notNullValue()));
        assertThat("size is not what we expect", filteredCount == 1L);
    }

    @Test
    public void testGetTotalCount() {
        Long totalCount = requestSearchRepository.getTotalCount();
        assertThat(totalCount, is(notNullValue()));
        assertThat("size is not what we expect", totalCount == 4L);
    }
}