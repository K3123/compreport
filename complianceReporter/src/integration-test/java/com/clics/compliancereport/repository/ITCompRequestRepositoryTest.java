package com.clics.compliancereport.repository;


import com.clics.compliancereport.common.AbstractTestCase;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.domain.CompRequest;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@DatabaseSetup("/META-INF/comprequest-dataset-sql.xml")
public class ITCompRequestRepositoryTest extends AbstractTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(ITCompRequestRepositoryTest.class);
    @Autowired
    private CompRequestRepository compRequestRepository;


    @Test
    public void testGetAllRequests(){
        List<CompRequest> list = compRequestRepository.getRequestsBySSOId(TestConstants.TEST_SSO);
        assertThat(list, is(notNullValue()));
        assertThat("list is empty", list.size() != 0);

    }


}