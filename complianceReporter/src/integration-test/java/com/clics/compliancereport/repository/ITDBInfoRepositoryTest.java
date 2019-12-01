package com.clics.compliancereport.repository;


import com.clics.compliancereport.common.AbstractTestCase;
import com.clics.compliancereport.common.TestConstants;
import com.clics.compliancereport.domain.DBInfo;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;



@DatabaseSetup("/META-INF/dbinventory-dataset-sql.xml")
public class ITDBInfoRepositoryTest extends AbstractTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(ITDBInfoRepositoryTest.class);
    @Autowired
    private DBInfoRepository compReportRepository;

    @Test
    public void testFindByUsageNotAndNameLike(){
        List<DBInfo> list = compReportRepository.findByStatusNotAndNameLikeOrderByNameAsc(TestConstants.RETIRED, "ACB%");
        assertThat(list, is(notNullValue()));
        assertEquals(list.size(), 2);

    }

    @Test
    public void testFindByUsageNotAndName(){
        List<DBInfo> list = compReportRepository.findByStatusNotAndName(TestConstants.RETIRED, "PMSACB93");
        assertThat(list, is(notNullValue()));
        assertEquals(list.size(), 1);
    }

    @Test
    public void testFindByUsageNotAndIdIn(){
        List<DBInfo> list = compReportRepository.findByStatusNotAndIdIn(TestConstants.RETIRED, 545L);
        assertThat(list, is(notNullValue()));
        assertEquals(list.size(), 1);
    }

    @Test
    public void testFindByStatusNot(){
        List<DBInfo> list = compReportRepository.findByStatusNot(TestConstants.RETIRED);
        assertThat(list, is(notNullValue()));
        assertEquals(list.size(), 3);
    }
}