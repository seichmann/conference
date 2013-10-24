package com.prodyna.conference.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.ConferenceService;

@Named(value = "scheduleOverviewController")
@SessionScoped
public class ScheduleOverviewController extends AbstractController {

	private DefaultScheduleModel model = new DefaultScheduleModel();

	@Inject
	private ConferenceService conferenceService;

	private List<Conference> conferences;

	private Conference selectedConference;

	public void loadData() {
		conferences = conferenceService.getAllConferences();
	}

	public DefaultScheduleModel getModel() {
		return model;
	}

	public List<Conference> getConferences() {
		return conferences;
	}

	public Conference getSelectedConference() {
		return selectedConference;
	}

	public void setSelectedConference(Conference selectedConference) {
		this.selectedConference = selectedConference;
	}

	public void updateSchedule() {
		model = new DefaultScheduleModel();
		if (selectedConference != null) {
			for (Talk talk : selectedConference.getTalks()) {
				model.addEvent(new DefaultScheduleEvent(talk.getName(), talk
						.getStart(), talk.getEnd()));
			}
		}
	}
}
