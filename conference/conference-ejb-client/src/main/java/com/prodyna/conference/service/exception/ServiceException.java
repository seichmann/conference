package com.prodyna.conference.service.exception;

/**
 * Abstract Base class for all Checked ServiceExceptions.
 * 
 * @author prodyna
 * 
 */
public class ServiceException extends Exception {

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}
