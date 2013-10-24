package com.prodyna.conference.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.TalkService;

/**
 * Shows details of one Talk with associated room and speaker list.
 */
@Named(value = "talkOverviewController")
@SessionScoped
public class TalkOverviewController extends AbstractController {

	private static final long serialVersionUID = 8790929507576888837L;

	@Inject
	private TalkService talkService;

	@Inject
	private NavigationOverviewController navigationOverviewController;

	private Talk talk;

	public void loadData() {
		try {
			// DOES NOT work correctly because ViewScoped Bean is
			// constructed multiple times on page load due to bug in JSF.
			// facesContext.getExternalContext().getFlash().put("talk",
			// talk);
			// facesContext.getExternalContext().getFlash().keep("talk");

			// postconstruct is also executed multiple times and therefore bad
			// solutionS

			Long talkId = navigationOverviewController.getTalkId();
			if (talkId != null) {
				talk = talkService.loadTalkEager(talkId);
			}
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	// **************** GETTER / SETTER *****************/
	public Talk getTalk() {
		return talk;
		// return (Talk)
		// facesContext.getExternalContext().getFlash().get("talk");
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}

	public List<Speaker> getSpeakers() {
		return new ArrayList<Speaker>(talk.getSpeakers());
	}
}
