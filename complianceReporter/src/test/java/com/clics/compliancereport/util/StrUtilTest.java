package com.clics.compliancereport.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;


public class StrUtilTest {

    private static String DELIMITER = ",";

    @Test
    public void testTokenize(){
        String  test = "1811:ACCC,2929:QWGATTSII";
        Object[] list = StrUtil.tokenize(test, DELIMITER);
        assertEquals((String)list[0], "1811:ACCC");
        assertEquals((String)list[1], "2929:QWGATTSII");
    }

    @Test
    public void testTokenize2(){
        String  test = "1811:ACCC,2929:QWGATTSII";
        Object[] list = StrUtil.tokenize(test, null);
        assertNull(list);
    }

    @Test
    public void testTokenize3(){
        Object[] list = StrUtil.tokenize(null, null);
        assertNull(list);
    }

    @Test
    public void testTokenize4(){
        Object[] list = StrUtil.tokenize(null, DELIMITER);
        assertNull(list);
    }


    @Test
    public void testRemoveSpaces() {
        String  spaceStr = "[ 1811 : ACCC, 2929 : QWGATTSII     ]";
        String expectedStr = "[1811:ACCC,2929:QWGATTSII]";
        assertEquals(StrUtil.removeSpaces(spaceStr), expectedStr);
    }

    @Test(expected = IllegalAccessException.class)
    public void testInstantiation() throws IllegalAccessException, InstantiationException {
        StrUtil.class.newInstance();
    }

}
