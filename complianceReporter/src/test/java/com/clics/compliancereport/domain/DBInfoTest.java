package com.clics.compliancereport.domain;


import com.clics.compliancereport.common.TestConstants;
import org.junit.Test;

import static junit.framework.Assert.*;

public class DBInfoTest {


    @Test
    public void testManditoryFields() {
        DBInfo built = DBInfo.getBuilder(TestConstants.DB_TEST_NAME1, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI).build();
        assertNull(built.getServerName());
        assertNull(built.getPort());
        assertNull(built.getLocation());
        assertNull(built.getDesc());
        assertNull(built.getId());
        assertNull(built.getStatus());
        assertNull(built.getVersion());
        assertNotNull(built.getName());
        assertEquals(built.getName(), TestConstants.DB_TEST_NAME1);
        assertNotNull(built.getUsage());
        assertEquals(built.getUsage(), TestConstants.DB_TEST_USAGE1);
        assertNotNull(built.getVirtualSqlInstance());
        assertEquals(built.getVirtualSqlInstance(), TestConstants.DB_TEST_VSI);
    }

    @Test
    public void testManditoryFieldsPlusServerNamePort() {
        DBInfo built = DBInfo.getBuilder(TestConstants.DB_TEST_NAME1, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI)
                .port(TestConstants.DB_PORT).serverName(TestConstants.DB_SERVER_NAME).build();
        assertEquals(built.getServerName(), TestConstants.DB_SERVER_NAME);
        assertEquals(built.getPort(), TestConstants.DB_PORT);
        assertNull(built.getLocation());
        assertNull(built.getDesc());
        assertNull(built.getId());
        assertNull(built.getStatus());
        assertNull(built.getVersion());
        assertNotNull(built.getName());
        assertEquals(built.getName(), TestConstants.DB_TEST_NAME1);
        assertNotNull(built.getUsage());
        assertEquals(built.getUsage(), TestConstants.DB_TEST_USAGE1);
        assertNotNull(built.getVirtualSqlInstance());
        assertEquals(built.getVirtualSqlInstance(), TestConstants.DB_TEST_VSI);
    }


    @Test
    public void testManditoryFieldsPlusServerNamePortId() {
        DBInfo built = DBInfo.getBuilder(TestConstants.DB_TEST_NAME1, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI)
                .port(TestConstants.DB_PORT).serverName(TestConstants.DB_SERVER_NAME).id(TestConstants.DB_TEST_ID).build();
        assertEquals(built.getServerName(), TestConstants.DB_SERVER_NAME);
        assertEquals(built.getPort(), TestConstants.DB_PORT);
        assertNull(built.getLocation());
        assertNull(built.getDesc());
        assertEquals(built.getId(), TestConstants.DB_TEST_ID);
        assertNull(built.getStatus());
        assertNull(built.getVersion());
        assertNotNull(built.getName());
        assertEquals(built.getName(), TestConstants.DB_TEST_NAME1);
        assertNotNull(built.getUsage());
        assertEquals(built.getUsage(), TestConstants.DB_TEST_USAGE1);
        assertNotNull(built.getVirtualSqlInstance());
        assertEquals(built.getVirtualSqlInstance(), TestConstants.DB_TEST_VSI);
    }

    @Test
    public void testManditoryFieldsPlusServerNamePortIdLocation() {
        DBInfo built = DBInfo.getBuilder(TestConstants.DB_TEST_NAME1, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI)
                .port(TestConstants.DB_PORT).serverName(TestConstants.DB_SERVER_NAME).id(TestConstants.DB_TEST_ID)
                .location(TestConstants.DB_LOCATION).build();
        assertEquals(built.getServerName(), TestConstants.DB_SERVER_NAME);
        assertEquals(built.getPort(), TestConstants.DB_PORT);
        assertEquals(built.getLocation(), TestConstants.DB_LOCATION);
        assertNull(built.getDesc());
        assertEquals(built.getId(), TestConstants.DB_TEST_ID);
        assertNull(built.getStatus());
        assertNull(built.getVersion());
        assertNotNull(built.getName());
        assertEquals(built.getName(), TestConstants.DB_TEST_NAME1);
        assertNotNull(built.getUsage());
        assertEquals(built.getUsage(), TestConstants.DB_TEST_USAGE1);
        assertNotNull(built.getVirtualSqlInstance());
        assertEquals(built.getVirtualSqlInstance(), TestConstants.DB_TEST_VSI);
    }


    @Test
    public void testManditoryFieldsPlusServerNamePortIdLocationStatus() {
        DBInfo built = DBInfo.getBuilder(TestConstants.DB_TEST_NAME1, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI)
                .port(TestConstants.DB_PORT).serverName(TestConstants.DB_SERVER_NAME).id(TestConstants.DB_TEST_ID)
                .location(TestConstants.DB_LOCATION).status(TestConstants.DB_STATUS).build();
        assertEquals(built.getServerName(), TestConstants.DB_SERVER_NAME);
        assertEquals(built.getPort(), TestConstants.DB_PORT);
        assertEquals(built.getLocation(), TestConstants.DB_LOCATION);
        assertNull(built.getDesc());
        assertEquals(built.getId(), TestConstants.DB_TEST_ID);
        assertEquals(built.getStatus(), TestConstants.DB_STATUS);
        assertNull(built.getVersion());
        assertNotNull(built.getName());
        assertEquals(built.getName(), TestConstants.DB_TEST_NAME1);
        assertNotNull(built.getUsage());
        assertEquals(built.getUsage(), TestConstants.DB_TEST_USAGE1);
        assertNotNull(built.getVirtualSqlInstance());
        assertEquals(built.getVirtualSqlInstance(), TestConstants.DB_TEST_VSI);
    }

    @Test
    public void testToString(){
        DBInfo built = DBInfo.getBuilder(TestConstants.DB_TEST_NAME1, TestConstants.DB_TEST_USAGE1, TestConstants.DB_TEST_VSI)
               .build();
        assertTrue(built.toString().contains(TestConstants.DB_TEST_NAME1));
        assertTrue(built.toString().contains(TestConstants.DB_TEST_USAGE1));
        assertTrue(built.toString().contains(TestConstants.DB_TEST_VSI));
    }
}
