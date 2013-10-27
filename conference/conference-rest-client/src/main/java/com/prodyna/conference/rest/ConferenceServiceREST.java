/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prodyna.conference.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.exception.ConferenceServiceException;

/**
 * REST-Implementation for {@link ConferenceService}.
 * 
 * @author Stephan Eichmann
 * 
 */
@Path("/conference")
@RequestScoped
public class ConferenceServiceREST {

	@Inject
	private ConferenceService service;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Conference saveConference(Conference conference)
			throws ConferenceServiceException {
		return service.saveConference(conference);
		// return Response.ok(service.saveConference(conference)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Conference> getAllConferences() {
		return service.getAllConferences();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteConference(Conference conference) {
		service.deleteConference(conference);
	}

}
