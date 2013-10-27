package com.prodyna.conference.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.prodyna.conference.controller.AbstractController;
import com.prodyna.conference.model.Room;
import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.RoomService;
import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.TalkService;

/**
 * CRUD View for entity {@link Talk}.
 * 
 * @author Stephan Eichmann
 * 
 */
@Named(value = "talkController")
@SessionScoped
public class TalkController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7079315195760451446L;

	@Inject
	private TalkService talkService;

	@Inject
	private SpeakerService speakerService;

	@Inject
	private RoomService roomService;

	private Talk editTalk;

	private List<Speaker> assignedSpeakers;

	private List<Room> allRooms;

	private List<Speaker> allSpeakers;

	public TalkController() {
		createNew();
	}

	public List<Talk> getList() {
		try {
			return talkService.getAllTalks();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
			return new ArrayList<Talk>();
		}
	}

	public void save() {
		try {
			talkService.saveTalk(editTalk, assignedSpeakers);
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void delete() {
		try {
			talkService.deleteTalk(editTalk);

			createNew();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void onRowSelect(SelectEvent event) {
		editTalk = (Talk) event.getObject();

		// Load Speakers
		try {
			assignedSpeakers = talkService.getSpeakersByTalk(editTalk.getId());
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void onRowUnselect(UnselectEvent event) {
		createNew();
	}

	public void createNew() {
		editTalk = new Talk();

		assignedSpeakers = null;
	}

	public List<Speaker> loadSpeakers() {
		try {
			allSpeakers = speakerService.getAllSpeakers();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
			allSpeakers = new ArrayList<Speaker>();
		}
		return allSpeakers;
	}

	public List<Room> loadRooms() {
		try {
			allRooms = roomService.getAllRooms();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
			allRooms = new ArrayList<Room>();
		}
		return allRooms;
	}

	// **************** GETTER / SETTER *****************/

	public Talk getEditTalk() {
		return editTalk;
	}

	public void setEditTalk(Talk editTalk) {
		this.editTalk = editTalk;
	}

	public List<Room> getAllRooms() {
		return allRooms;
	}

	public void setAllRooms(List<Room> allRooms) {
		this.allRooms = allRooms;
	}

	public List<Speaker> getAllSpeakers() {
		return allSpeakers;
	}

	public void setAllSpeakers(List<Speaker> allSpeakers) {
		this.allSpeakers = allSpeakers;
	}

	public List<Speaker> getAssignedSpeakers() {
		return assignedSpeakers;
	}

	public void setAssignedSpeakers(List<Speaker> assignedSpeakers) {
		this.assignedSpeakers = assignedSpeakers;
	}
}
