package com.prodyna.conference.service.exception;

import com.prodyna.conference.model.Talk;

/**
 * Exception indicates date conflicts of a speaker.
 * 
 * @author prodyna
 * 
 */
public class SpeakerConstraintException extends ConferenceServiceException {

	public SpeakerConstraintException(Talk talk) {
		super("Speaker already assigned to talk " + talk.getName()
				+ " of same daytime.");
	}
}
