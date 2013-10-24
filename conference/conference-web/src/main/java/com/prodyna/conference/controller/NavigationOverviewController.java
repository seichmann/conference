package com.prodyna.conference.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.prodyna.conference.model.Room;
import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.util.NavigationConstants;

/**
 * Handles navigation of overview views and save state of selection in session.
 * 
 * @author Stephan Eichmann
 * 
 */
@Named(value = "navigationOverviewController")
@SessionScoped
public class NavigationOverviewController extends AbstractController {

	private Room room;

	private Speaker speaker;

	private Long talkId;

	/**
	 * Save room parameter in session and redirects to roomOverview.
	 * 
	 * @param room
	 */
	public String open(Room room) {
		this.room = room;
		return NavigationConstants.ROOM_OVERVIEW;
	}

	/**
	 * Save speaker parameter in session and redirects to speakerOverview
	 * 
	 * @param speaker
	 */
	public String open(Speaker speaker) {
		this.speaker = speaker;
		return NavigationConstants.SPEAKER_OVERVIEW;
	}

	/**
	 * Save talkId in session and redirects to talkOverview.
	 * 
	 * @param talkId
	 * @return
	 */
	public String open(Long talkId) {
		this.talkId = talkId;
		return NavigationConstants.TALK_OVERVIEW;
	}

	// **************** GETTER / SETTER ***************
	public Room getRoom() {
		return room;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public Long getTalkId() {
		return talkId;
	}
}
