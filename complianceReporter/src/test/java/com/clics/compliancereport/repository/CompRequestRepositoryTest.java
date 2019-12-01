package com.clics.compliancereport.repository;


import com.clics.compliancereport.common.AbstractTestCase;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.domain.CompRequest;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@DatabaseSetup("/META-INF/comprequest-dataset-sql.xml")
public class CompRequestRepositoryTest extends AbstractTestCase {

    @Autowired
    CompRequestRepository compRequestRepository;


    @Test
    public void testGetAllRequests() {
        List<CompRequest> requestList = compRequestRepository.getRequestsBySSOId(TestConstants.TEST_SSO);
        assertThat(requestList, is(notNullValue()));
        assertThat("size is not what we expect", requestList.size() == 1);
    }
}