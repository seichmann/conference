package com.prodyna.conference.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.prodyna.conference.model.Room;

@FacesConverter(value = "roomConverter")
public class RoomConverter implements Converter, Serializable {

	private static final long serialVersionUID = -8225321537019890949L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Application application = FacesContext.getCurrentInstance()
				.getApplication();
		List<Room> allRooms = (List<Room>) application.evaluateExpressionGet(
				context, "#{talkController.allRooms}", ArrayList.class);

		// Convert to object
		if (value != null && !value.trim().isEmpty()) {
			Long id = Long.valueOf(value);
			for (Room room : allRooms) {
				if (room.getId().equals(id)) {
					return room;
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
			return String.valueOf(((Room) value).getId());
		} else {
			return "";
		}

	}

}