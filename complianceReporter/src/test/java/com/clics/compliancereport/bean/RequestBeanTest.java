package com.clics.compliancereport.bean;

import com.clics.compliancereport.common.TestUtil;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class RequestBeanTest {

    @Test
    public void testGetRequests() throws Exception {
        RequestBean requestBean = new RequestBean();
        requestBean.setRequests(TestUtil.getTestRequests());
        assertEquals(requestBean.getRequests().size(), 3);
    }

    @Test
    public void testGetRequests2() throws Exception {
        RequestBean requestBean = new RequestBean(TestUtil.getTestRequests());
        assertEquals(requestBean.getRequests().size(), 3);
    }


} 
