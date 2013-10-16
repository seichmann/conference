package com.prodyna.conference.service.exception;

import com.prodyna.conference.model.Talk;

/**
 * Exception indicates date conflicts of a speaker.
 * 
 * @author prodyna
 * 
 */
public class RoomConstraintException extends ConferenceServiceException {

	public RoomConstraintException(Talk talk) {
		super("Speaker already assigned to talk " + talk.getName()
				+ " of same daytime.");
	}
}
