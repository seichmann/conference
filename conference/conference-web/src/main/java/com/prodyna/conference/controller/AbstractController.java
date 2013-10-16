package com.prodyna.conference.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.prodyna.conference.exception.DefaultExceptionHandler;

public class AbstractController implements Serializable {

	@Inject
	protected DefaultExceptionHandler exceptionHandler;
	
	@Inject
	private FacesContext facesContext;
}
