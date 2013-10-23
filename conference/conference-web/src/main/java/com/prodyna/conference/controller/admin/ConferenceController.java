package com.prodyna.conference.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.prodyna.conference.controller.AbstractController;
import com.prodyna.conference.model.Conference;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.TalkService;

/**
 * CRUD View for entity {@link Conference}.
 * 
 * @author Stephan Eichmann
 * 
 */
@Named(value = "conferenceController")
@SessionScoped
public class ConferenceController extends AbstractController {

	private static final long serialVersionUID = 7079315195760451446L;

	@Inject
	private ConferenceService conferenceService;

	@Inject
	private TalkService talkService;

	private Conference editConference;

	private List<Talk> allTalks;

	public ConferenceController() {
		createNew();
	}

	/**
	 * Gets all Conferences.
	 * 
	 * @return
	 */
	public List<Conference> getList() {
		try {
			return conferenceService.getAllConferences();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
			return new ArrayList<Conference>();
		}
	}

	public void save() {
		try {
			conferenceService.saveConference(editConference);
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}

	}

	public void delete() {
		try {
			conferenceService.deleteConference(editConference);

			createNew();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void onRowSelect(SelectEvent event) {
		editConference = (Conference) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		createNew();
	}

	public void createNew() {
		editConference = new Conference();
		editConference.setTalks(new ArrayList<Talk>());
	}

	public List<Talk> loadTalks() {
		try {
			allTalks = talkService.getAllTalks();
		} catch (Throwable t) {
			allTalks = new ArrayList<Talk>();
			exceptionHandler.handle(t);
		}

		return allTalks;
	}

	// **************** GETTER / SETTER *****************/

	public List<Talk> getAllTalks() {
		return allTalks;
	}

	public void setAllTalks(List<Talk> allTalks) {
		this.allTalks = allTalks;
	}

	public Conference getEditConference() {
		return editConference;
	}

	public void setEditConference(Conference editConference) {
		this.editConference = editConference;
	}
}
