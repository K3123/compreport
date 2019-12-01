package com.clics.compliancereport.exception;


import org.junit.Test;

import javax.persistence.PersistenceException;

import static junit.framework.Assert.assertEquals;

public class ServiceExceptionTest {
    private static final String ERROR_MSG = "Error occurred";
    private ServiceException exception;


    @Test
    public void testException() {
         exception = new ServiceException(ERROR_MSG);
         assertEquals(exception.getMessage(), ERROR_MSG);
    }

    @Test
    public void testException2() {
        PersistenceException persistenceException = new PersistenceException();
        exception = new ServiceException(ERROR_MSG, persistenceException);
        assertEquals(exception.getMessage(), ERROR_MSG);
        assertEquals(exception.getCause(), persistenceException);
    }

    @Test
    public void testException3() {
        exception = new ServiceException();
        assertEquals(exception.getMessage(), null);
        assertEquals(exception.getCause(), null);
    }
}
