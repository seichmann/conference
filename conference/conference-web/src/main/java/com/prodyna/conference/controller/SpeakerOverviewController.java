package com.prodyna.conference.controller;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.TalkService;

/**
 * Shows one speaker with associated talks.
 */
@Named(value = "speakerOverviewController")
@SessionScoped
public class SpeakerOverviewController extends AbstractController {

	private static final long serialVersionUID = 8790929507576888837L;

	@Inject
	private TalkService talkService;

	@Inject
	private NavigationOverviewController navigationOverviewController;

	private List<Talk> talks;

	public void loadData() {
		try {
			if (getSpeaker() != null) {
				talks = talkService.getTalksBySpeaker(getSpeaker().getId());
			}
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	// **************** GETTER / SETTER *****************/
	public Speaker getSpeaker() {
		return navigationOverviewController.getSpeaker();
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}
}
