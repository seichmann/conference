package com.prodyna.conference.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.prodyna.conference.exception.DefaultExceptionHandler;

/**
 * Abstract base class for all controllers.
 * 
 * @author Stephan Eichmann
 * 
 */
public class AbstractController implements Serializable {

	protected static final String NULL_VALUE = "-";

	@Inject
	protected DefaultExceptionHandler exceptionHandler;

	@Inject
	protected FacesContext facesContext;

}
