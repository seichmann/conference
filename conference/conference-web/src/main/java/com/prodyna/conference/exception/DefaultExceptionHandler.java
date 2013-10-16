package com.prodyna.conference.exception;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.prodyna.conference.service.exception.ConferenceServiceException;

@Named
public class DefaultExceptionHandler implements Serializable {

	public void handle(Throwable t) {
		String errorMessage = getRootErrorMessage(t);
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				errorMessage, "Please ask your System Administrator.");
		FacesContext.getCurrentInstance().addMessage(null, m);
	}

	private String getRootErrorMessage(Throwable t) {
		// Start with the exception and recurse to find the root cause
		Throwable root = null;
		while (t != null) {
			root = t;
			t = t.getCause();
		}

		if (t instanceof ConferenceServiceException) {
			return "Validation Exception: " + root.getLocalizedMessage();
		} else {
			return "Unexpected error occured: " + root.getLocalizedMessage();
		}
	}
}
