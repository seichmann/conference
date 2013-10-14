package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.model.Conference;

public interface ConferenceService {

	List<Conference> getAllConferences();

	void deleteConference(Conference conference);

	Conference saveConference(Conference conference);
}
