package com.prodyna.conference.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;

import com.prodyna.conference.interceptor.Monitored;
import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.exception.ConferenceServiceException;
import com.prodyna.conference.service.exception.RoomConstraintException;
import com.prodyna.conference.service.exception.SpeakerConstraintException;

@Stateless
@Monitored
public class TalkServiceBean implements TalkService {

	@Inject
	private EntityManager em;

	@Override
	public List<Talk> getAllTalks() {
		List<Talk> resultList = em.createNamedQuery("Talk.All", Talk.class)
				.getResultList();
		return resultList;
	}

	@Override
	public void deleteTalk(Talk talk) {
		Talk loadedtalk = em.find(Talk.class, talk.getId());

		if (loadedtalk != null) {
			em.remove(loadedtalk);
		}

	}

	@Override
	public Talk saveTalk(Talk talk) throws ConferenceServiceException {
		// Validate Constraints
		List<Talk> talks = getTalks(talk.getStart(), talk.getEnd());

		if (talk.getSpeakers() != null && !talk.getSpeakers().isEmpty()) {
			for (Speaker speaker : talk.getSpeakers()) {
				for (Talk existingTalk : talks) {
					if (existingTalk.getSpeakers() != null
							&& existingTalk.getSpeakers().contains(speaker)) {
						throw new SpeakerConstraintException(existingTalk);
					}
				}
			}
		}

		if (talk.getRoom() != null) {
			for (Talk existingTalk : talks) {
				if (existingTalk.getRoom() != null
						&& existingTalk.getRoom().equals(talk.getRoom())) {
					throw new RoomConstraintException(existingTalk);
				}
			}
		}

		Talk result = em.merge(talk);
		return result;
	}

	@Override
	public Talk loadTalkEager(Talk talk) {
		Talk result = em.find(Talk.class, talk.getId());

		// Trigger query Speaker List
		Hibernate.initialize(result.getSpeakers());

		return result;
	}

	@Override
	public List<Talk> getTalks(Date start, Date end) {
		TypedQuery<Talk> query = em
				.createNamedQuery("Talk.ByDates", Talk.class);
		query.setParameter("start", start);
		query.setParameter("end", end);
		return query.getResultList();
	}

	// @Override
	// public TalkSpeakerRelation assignSpeaker(Talk talk, Speaker speaker) {
	// // TalkSpeakerRelation relation = new TalkSpeakerRelation();
	// relation.setTalk(talk);
	// relation.setSpeaker(speaker);
	//
	// // return em.merge(relation);
	// }

	// @Override
	// public Talk loadSpeakersByTalk(Talk talk) {
	// TypedQuery<TalkSpeakerRelation> query = em.createNamedQuery(
	// "TalkSpeakerRelation.ByTalkId", TalkSpeakerRelation.class);
	// query.setParameter("talk_id", talk.getId());
	// List<TalkSpeakerRelation> talkSpeakerRelations =
	// query.getResultList();
	// talk.setSpeakerRelations(new
	// HashSet<TalkSpeakerRelation>(talkSpeakerRelations));
	// return talk;
	// }
}
