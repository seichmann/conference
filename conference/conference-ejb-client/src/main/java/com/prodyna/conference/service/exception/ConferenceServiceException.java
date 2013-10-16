package com.prodyna.conference.service.exception;

/**
 * Abstract Base class for all Checked ServiceExceptions.
 * 
 * @author prodyna
 * 
 */
public class ConferenceServiceException extends Exception {

	public ConferenceServiceException(String message) {
		super(message);
	}

	public ConferenceServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConferenceServiceException(Throwable cause) {
		super(cause);
	}
}
