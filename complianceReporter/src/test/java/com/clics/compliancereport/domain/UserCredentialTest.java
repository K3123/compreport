package com.clics.compliancereport.domain;

import com.clics.compliancereport.common.Constants;
import com.clics.compliancereport.common.TestConstants;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class UserCredentialTest {

    private static final String NAME = "John Doe";
    private static final String GROUPID = Constants.ADMIN_USER_GROUP;


    @Test
    public void testManditoryFields() {
        UserCredential built = UserCredential.getBuilder(TestConstants.TEST_SSO, TestConstants.TEST_EMAIL).build();
        assertNull(built.getName());
        assertNull(built.getGroupid());
        assertEquals(built.getSsoid(), TestConstants.TEST_SSO);
        assertEquals(built.getEmail(), TestConstants.TEST_EMAIL);
    }

    @Test
    public void testManditoryFieldsPlusName() {
        UserCredential built = UserCredential.getBuilder(TestConstants.TEST_SSO, TestConstants.TEST_EMAIL).name(NAME).build();
        assertEquals(built.getName(), NAME);
        assertNull(built.getGroupid());
        assertEquals(built.getSsoid(), TestConstants.TEST_SSO);
        assertEquals(built.getEmail(), TestConstants.TEST_EMAIL);
    }

    @Test
    public void testManditoryFieldsPLusNameGoupId() {
        UserCredential built = UserCredential.getBuilder(TestConstants.TEST_SSO, TestConstants.TEST_EMAIL).name(NAME).groupid(GROUPID).build();
        assertEquals(built.getName(), NAME);
        assertEquals(built.getGroupid(), GROUPID);
        assertEquals(built.getSsoid(), TestConstants.TEST_SSO);
        assertEquals(built.getEmail(), TestConstants.TEST_EMAIL);
    }


    @Test
    public void testManditoryFieldsPLusGoupId() {
        UserCredential built = UserCredential.getBuilder(TestConstants.TEST_SSO, TestConstants.TEST_EMAIL).groupid(GROUPID).build();
        assertNull(built.getName());
        assertEquals(built.getGroupid(), GROUPID);
        assertEquals(built.getSsoid(), TestConstants.TEST_SSO);
        assertEquals(built.getEmail(), TestConstants.TEST_EMAIL);
    }

    @Test
    public void testSetEmail() {
        UserCredential credential = new UserCredential();
        credential.setEmail(TestConstants.TEST_EMAIL);
        assertEquals(credential.getEmail(), TestConstants.TEST_EMAIL);
    }

    @Test
    public void testSetGroupId() {
        UserCredential credential = new UserCredential();
        credential.setGroupid(Constants.STANDARD_USER_GROUP);
        assertEquals(credential.getGroupid(), Constants.STANDARD_USER_GROUP);
    }

    @Test
    public void testSetName() {
        UserCredential credential = new UserCredential();
        credential.setName(Constants.FAKE_FULLNAME);
        assertEquals(credential.getName(), Constants.FAKE_FULLNAME);
    }

    @Test
    public void testSSOId() {
        UserCredential credential = new UserCredential();
        credential.setSsoid(Constants.FAKE_SSO_ID);
        assertEquals(credential.getSsoid(), Constants.FAKE_SSO_ID);
    }

    @Test
    public void testToString(){
        UserCredential built = UserCredential.getBuilder(TestConstants.TEST_SSO, TestConstants.TEST_EMAIL).groupid(GROUPID).build();
        assertTrue(built.toString().contains(TestConstants.TEST_SSO));
        assertTrue(built.toString().contains(TestConstants.TEST_EMAIL));
        assertTrue(built.toString().contains(GROUPID));
    }

}
