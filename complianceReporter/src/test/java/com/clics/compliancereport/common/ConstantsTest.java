package com.clics.compliancereport.common;


import org.junit.Test;


public class ConstantsTest {
   @Test(expected = IllegalAccessException.class)
   public void testInstantiation() throws IllegalAccessException, InstantiationException {
        Constants.class.newInstance();
   }
}
