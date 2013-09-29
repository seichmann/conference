package com.prodyna.conference.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.conference.interceptor.Monitored;
import com.prodyna.conference.model.Conference;

@Stateless
@Monitored
public class ConferenceServiceBean implements ConferenceService {

	@Inject
	private EntityManager em;

	@Override
	public List<Conference> getAllConferences() {
		return em.createNamedQuery("Conference.All", Conference.class)
				.getResultList();
	}
}
