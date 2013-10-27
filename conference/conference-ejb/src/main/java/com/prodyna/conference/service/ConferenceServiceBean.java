package com.prodyna.conference.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.conference.interceptor.Monitored;
import com.prodyna.conference.model.Conference;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.exception.ConferenceConstraintException;
import com.prodyna.conference.service.exception.ConferenceServiceException;
import com.prodyna.conference.util.DateUtil;

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

	@Override
	public Conference saveConference(Conference conference)
			throws ConferenceServiceException {
		// Validate Constraints (Talks not in daterange of conference)
		if (conference.getTalks() != null) {
			for (Talk talk : conference.getTalks()) {
				if (!DateUtil.inRange(talk.getStart(), conference.getStart(),
						conference.getEnd())
						|| !DateUtil.inRange(talk.getEnd(),
								conference.getStart(), conference.getEnd())) {
					throw new ConferenceConstraintException(talk);
				}
			}

		}

		return em.merge(conference);
	}

	@Override
	public void deleteConference(Conference conference) {
		Conference loadedConference = em.find(Conference.class,
				conference.getId());

		if (loadedConference != null) {
			em.remove(loadedConference);
		}
	}
}
