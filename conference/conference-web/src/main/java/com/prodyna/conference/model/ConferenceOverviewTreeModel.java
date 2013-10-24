package com.prodyna.conference.model;

import com.prodyna.conference.controller.ConferenceOverviewController;

/**
 * Client model for Tree Structure in {@link ConferenceOverviewController}.
 * 
 * @author Stephan Eichmann
 * 
 */
public class ConferenceOverviewTreeModel {

	private String name;

	private String talkCount;

	private String talkDetails;

	private Long talkId;

	public Long getTalkId() {
		return talkId;
	}

	public void setTalkId(Long talkId) {
		this.talkId = talkId;
	}

	public ConferenceOverviewTreeModel(String name, String talkCount,
			String talkDetails, Long talkId) {
		super();
		this.name = name;
		this.talkCount = talkCount;
		this.talkDetails = talkDetails;
		this.talkId = talkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTalkCount() {
		return talkCount;
	}

	public void setTalkCount(String talkCount) {
		this.talkCount = talkCount;
	}

	public String getTalkDetails() {
		return talkDetails;
	}

	public void setTalkDetails(String talkDetails) {
		this.talkDetails = talkDetails;
	}

}
