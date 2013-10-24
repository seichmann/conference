package com.prodyna.conference.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.conference.model.Room;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.RoomService;
import com.prodyna.conference.service.TalkService;

/**
 * Shows details of one room with associated talks.
 * 
 * @author Stephan Eichmann
 * 
 */
@Named(value = "roomOverviewController")
@SessionScoped
public class RoomOverviewController extends AbstractController {

	private static final long serialVersionUID = 8790929507576888837L;

	@Inject
	private TalkService talkService;

	@Inject
	private RoomService roomService;

	@Inject
	private NavigationOverviewController navigationOverviewController;

	private List<Talk> talks;

	public void loadData() {
		if (getRoom() != null) {
			this.talks = talkService.getTalksByRoom(getRoom().getId());
		}
	}

	// **************** GETTER / SETTER *****************/

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

	public Room getRoom() {
		return navigationOverviewController.getRoom();
	}
}
