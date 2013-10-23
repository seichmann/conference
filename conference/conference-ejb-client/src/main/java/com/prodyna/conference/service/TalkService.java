package com.prodyna.conference.service;

import java.util.Date;
import java.util.List;

import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.exception.ConferenceServiceException;

/**
 * Mangement Interface for CRUD-Operations on {@link Talk}.
 * 
 * @author Stephan Eichmann
 * 
 */
public interface TalkService {

	/**
	 * Returns all talks with room assigned.
	 * 
	 * @return
	 */
	List<Talk> getAllTalks();

	/**
	 * Deletes given talk.
	 * 
	 * @param talk
	 */
	void deleteTalk(Talk talk);

	/**
	 * Create/Update given talk.
	 * 
	 * @param talk
	 * @return
	 * @throws ConferenceServiceException
	 *             semantic validation errors.
	 */
	Talk saveTalk(Talk talk) throws ConferenceServiceException;

	/**
	 * Loads speaker relation of given talk.
	 * 
	 * @param talk
	 * @return
	 */
	Talk loadTalkEager(Talk talk);

	/**
	 * Returns all talks which overlap with given date range.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	List<Talk> getTalks(Date start, Date end);

	/**
	 * Load talks with speaker relation.
	 * 
	 * @param talkId
	 * @return
	 */
	Talk loadTalkEager(Long talkId);

	/**
	 * Returns all talks which is hold by given speaker.
	 * 
	 * @param speakerId
	 * @return
	 */
	List<Talk> getTalksBySpeaker(Long speakerId);

	/**
	 * Returns all talks, which are located in given room.
	 * 
	 * @param roomId
	 * @return
	 */
	List<Talk> getTalksByRoom(Long roomId);
}
