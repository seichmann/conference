package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.model.Speaker;

/**
 * Management Interface for CRUD-Operations on {@link Speaker}.
 * 
 * @author Stephan Eichmann
 * 
 */
public interface SpeakerService {

	/**
	 * Returns all Speakers.
	 * 
	 * @return
	 */
	List<Speaker> getAllSpeakers();

	/**
	 * Delete given speaker.
	 * 
	 * @param Speaker
	 */
	void deleteSpeaker(Speaker Speaker);

	/**
	 * Create/Update given speaker.
	 * 
	 * @param Speaker
	 * @return
	 */
	Speaker saveSpeaker(Speaker Speaker);
}
