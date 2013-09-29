package com.prodyna.conference.controller;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class ConferenceController {

	@Inject
	private FacesContext facesContext;
}
