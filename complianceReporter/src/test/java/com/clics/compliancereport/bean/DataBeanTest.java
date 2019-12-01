package com.clics.compliancereport.bean;

import com.clics.compliancereport.common.TestUtil;
import com.clics.compliancereport.domain.DBInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;


public class DataBeanTest {

    private DataBean dataBean;

    @Before
    public void before() throws Exception {
        List<DBInfo> dbInfoList = TestUtil.getTestDBInfoList();
        dataBean = new DataBean();
        dataBean.setDbInfos(dbInfoList);
    }

    @After
    public void after() throws Exception {
        dataBean = null;
    }


    @Test
    public void testGetDbInfos() throws Exception {
        assertEquals(dataBean.getDbInfos().size(), 3);
    }


    @Test
    public void testSetDbInfos() throws Exception {
        List<DBInfo> dbInfoList = TestUtil.getTestDBInfo();
        dataBean = new DataBean();
        dataBean.setDbInfos(dbInfoList);
        assertEquals(dataBean.getDbInfos().size(), 1);
    }


    @Test
    public void testSetDbInfos2() throws Exception {
        List<DBInfo> dbInfoList = TestUtil.getTestDBInfo();
        dataBean = new DataBean(dbInfoList);
        assertEquals(dataBean.getDbInfos().size(), 1);
    }


} 
