package com.prodyna.conference.service.exception;

import com.prodyna.conference.model.Talk;

/**
 * Exception indicates date conflicts between talk and conference.
 * 
 * @author prodyna
 * 
 */
public class ConferenceConstraintException extends ConferenceServiceException {

	public ConferenceConstraintException(Talk talk) {
		super("Date range of Talk " + talk.getName()
				+ " is not included in conference date range.");
	}
}
