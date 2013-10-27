package com.prodyna.conference.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.prodyna.conference.model.Speaker;

/**
 * Converts from Speaker.id to entity {@link Speaker}. Uses cached data from
 * controller instance.
 * 
 * @author Stephan Eichmann
 * 
 */
@FacesConverter(value = "speakerConverter")
public class SpeakerConverter implements Converter, Serializable {

	private static final long serialVersionUID = -8225321537019890949L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Application application = FacesContext.getCurrentInstance()
				.getApplication();
		List<Speaker> allSpeakers = (List<Speaker>) application
				.evaluateExpressionGet(context,
						"#{talkController.allSpeakers}", ArrayList.class);

		// Convert to object
		if (value != null && !value.trim().isEmpty()) {
			Long id = Long.valueOf(value);
			for (Speaker speaker : allSpeakers) {
				if (speaker.getId().equals(id)) {
					return speaker;
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
			return String.valueOf(((Speaker) value).getId());
		} else {
			return "";
		}

	}

}