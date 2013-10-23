package com.prodyna.conference.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.prodyna.conference.model.Talk;

/**
 * Converts from Talk.id to entity {@link Talk}. Uses cached data from
 * controller instance.
 * 
 * @author Stephan Eichmann
 * 
 */
@FacesConverter(value = "talkConverter")
public class TalkConverter implements Converter, Serializable {

	private static final long serialVersionUID = -8225321537019890949L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Application application = FacesContext.getCurrentInstance()
				.getApplication();
		List<Talk> allTalks = (List<Talk>) application.evaluateExpressionGet(
				context, "#{conferenceController.allTalks}", ArrayList.class);

		// Convert to object
		if (value != null && !value.trim().isEmpty()) {
			Long id = Long.valueOf(value);
			for (Talk talk : allTalks) {
				if (talk.getId().equals(id)) {
					return talk;
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
			return String.valueOf(((Talk) value).getId());
		} else {
			return "";
		}

	}

}