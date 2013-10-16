package com.prodyna.conference.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.conference.interceptor.Monitored;
import com.prodyna.conference.model.Speaker;

@Stateless
@Monitored
public class SpeakerServiceBean implements SpeakerService {

	@Inject
	private EntityManager em;

	@Override
	public List<Speaker> getAllSpeakers() {
		return em.createNamedQuery("Speaker.All", Speaker.class)
				.getResultList();
	}

	@Override
	public Speaker saveSpeaker(Speaker Speaker) {
		return em.merge(Speaker);
	}

	@Override
	public void deleteSpeaker(Speaker Speaker) {
		Speaker loadedSpeaker = em.find(Speaker.class, Speaker.getId());

		if (loadedSpeaker != null) {
			em.remove(loadedSpeaker);
		}
	}
}
