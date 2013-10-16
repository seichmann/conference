package com.prodyna.conference.service;

import java.util.Date;
import java.util.List;

import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.exception.ConferenceServiceException;

public interface TalkService {

	List<Talk> getAllTalks();

	void deleteTalk(Talk talk);

	Talk saveTalk(Talk talk) throws ConferenceServiceException;

	Talk loadTalkEager(Talk talk);

	List<Talk> getTalks(Date start, Date end);
}
