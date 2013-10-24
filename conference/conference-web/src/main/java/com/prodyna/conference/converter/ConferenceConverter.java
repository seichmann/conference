package com.prodyna.conference.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.prodyna.conference.model.Conference;

/**
 * Handles Conference ID.
 * 
 * @author Stephan Eichmann
 * 
 */
@FacesConverter(value = "conferenceConverter")
public class ConferenceConverter implements Converter, Serializable {

	private static final long serialVersionUID = -8225321537019890949L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Application application = FacesContext.getCurrentInstance()
				.getApplication();
		List<Conference> conferences = (List<Conference>) application
				.evaluateExpressionGet(context,
						"#{scheduleOverviewController.conferences}",
						ArrayList.class);

		// Convert to object
		if (value != null && !value.trim().isEmpty()) {
			Long id = Long.valueOf(value);
			for (Conference conference : conferences) {
				if (conference.getId().equals(id)) {
					return conference;
				}
			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		// Convert to String
		if (value != null) {
			return String.valueOf(((Conference) value).getId());
		} else {
			return "";
		}

	}

}