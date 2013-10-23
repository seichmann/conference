package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.service.exception.ConferenceServiceException;

/**
 * Management interface for CRUD-Operations on {@link Conference}.
 * 
 * @author Stephan Eichmann
 * 
 */
public interface ConferenceService {

	/**
	 * Returns all conferences.
	 * 
	 * @return
	 */
	List<Conference> getAllConferences();

	/**
	 * Deletes given conferernce.
	 * 
	 * @param conference
	 */
	void deleteConference(Conference conference);

	/**
	 * Create / Update given conference.
	 * 
	 * @param conference
	 * @return
	 * @throws ConferenceServiceException
	 *             semantic validation erros.
	 */
	Conference saveConference(Conference conference)
			throws ConferenceServiceException;
}
