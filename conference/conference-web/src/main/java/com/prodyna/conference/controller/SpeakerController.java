package com.prodyna.conference.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.prodyna.conference.exception.DefaultExceptionHandler;
import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.service.SpeakerService;

@Named(value = "speakerController")
@SessionScoped
public class SpeakerController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7079315195760451446L;

	@EJB
	private SpeakerService speakerService;

	private Speaker editSpeaker;

	public SpeakerController() {
		createNew();
	}

	public List<Speaker> getList() {
		try {
			return speakerService.getAllSpeakers();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
			return new ArrayList<Speaker>();
		}
	}

	public void save() {
		try {
			speakerService.saveSpeaker(editSpeaker);
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void delete() {
		try {
			speakerService.deleteSpeaker(editSpeaker);

			createNew();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void onRowSelect(SelectEvent event) {
		editSpeaker = (Speaker) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		createNew();
	}

	public void createNew() {
		editSpeaker = new Speaker();
	}

	// Getter / Setter
	public Speaker getEditSpeaker() {
		return editSpeaker;
	}

	public void setEditSpeaker(Speaker editSpeaker) {
		this.editSpeaker = editSpeaker;
	}
}
