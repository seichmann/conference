package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.service.exception.ConferenceServiceException;

public interface ConferenceService {

	List<Conference> getAllConferences();

	void deleteConference(Conference conference);

	Conference saveConference(Conference conference)
			throws ConferenceServiceException;
}
