package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.model.Speaker;

public interface SpeakerService {

	List<Speaker> getAllSpeakers();

	void deleteSpeaker(Speaker Speaker);

	Speaker saveSpeaker(Speaker Speaker);
}
