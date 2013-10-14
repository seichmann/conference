package com.prodyna.conference.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.service.ConferenceService;

@Named(value = "conferenceController")
@SessionScoped
public class ConferencesController implements Serializable {

	@Inject
	private FacesContext facesContext;

	@EJB
	private ConferenceService conferenceService;

	private Conference editConference;

	public ConferencesController() {
		createNew();
	}

	/**
	 * Gets all Conferences.
	 * 
	 * @return
	 */
	public List<Conference> getList() {
		return conferenceService.getAllConferences();
	}

	public void save() {
		conferenceService.saveConference(editConference);

		createNew();
	}

	public void delete() {
		conferenceService.deleteConference(editConference);
		
		createNew();
	}

	 public void onRowSelect(SelectEvent event) {  
	       editConference = (Conference) event.getObject();
	    }  
	 
	 public void onRowUnselect(SelectEvent event) {  
		 createNew();
	 }  
	 
	 public void createNew() {
		 editConference = new Conference();
	}

	public Conference getEditConference() {
		return editConference;
	}

	public void setEditConference(Conference editConference) {
		this.editConference = editConference;
	}
}
