package com.clics.compliancereport.exception;


public final class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(final Throwable cause) {
        super(cause);
    }

}
