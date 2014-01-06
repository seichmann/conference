package com.prodyna.conference.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.prodyna.conference.interceptor.Monitored;
import com.prodyna.conference.model.QTalk;
import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.model.TalkSpeakerRelation;
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
		Talk loadedtalk = getTalk(talk.getId());

		if (loadedtalk != null) {
			unassignAllSpeakers(loadedtalk);
			new JPADeleteClause(em, QTalk.talk).execute();
		}
	}

	@Override
	public Talk saveTalk(Talk talk, List<Speaker> speakerList)
			throws ConferenceServiceException {
		// Validate Constraints
		List<Talk> talks = getTalks(talk.getStart(), talk.getEnd());

		if (speakerList != null && !speakerList.isEmpty()) {
			for (Speaker speaker : speakerList) {
				for (Talk existingTalk : talks) {
					List<Speaker> existingSpeakers = getSpeakersByTalk(existingTalk
							.getId());

					if (!isSameTalk(existingTalk, talk)
							&& existingSpeakers != null
							&& existingSpeakers.contains(speaker)) {
						throw new SpeakerConstraintException(existingTalk);
					}
				}
			}
		}

		if (talk.getRoom() != null) {
			for (Talk existingTalk : talks) {
				if (!isSameTalk(existingTalk, talk)
						&& existingTalk.getRoom() != null
						&& existingTalk.getRoom().equals(talk.getRoom())) {
					throw new RoomConstraintException(existingTalk);
				}
			}
		}

		Talk result = em.merge(talk);

		// Unassign when updated talk.
		if (talk.getId() != null) {
			unassignAllSpeakers(result);
		}

		if (speakerList != null && !speakerList.isEmpty()) {
			for (Speaker speaker : speakerList) {
				assignSpeaker(result, speaker);
			}
		}

		return result;
	}

	private void assignSpeaker(Talk talk, Speaker speaker) {
		TalkSpeakerRelation talkSpeakerRelation = new TalkSpeakerRelation();
		talkSpeakerRelation.setTalk(talk);
		talkSpeakerRelation.setSpeaker(speaker);
		em.persist(talkSpeakerRelation);
	}

	private void unassignAllSpeakers(Talk talk) {
		List<TalkSpeakerRelation> talkSpeakerRelations = getTalkSpeakerRelations(talk
				.getId());
		for (TalkSpeakerRelation talkSpeakerRelation : talkSpeakerRelations) {
			em.remove(talkSpeakerRelation);
		}
	}

	private boolean isSameTalk(Talk existingTalk, Talk talk) {
		return talk.getId() != null
				&& talk.getId().equals(existingTalk.getId());
	}

	@Override
	public List<Speaker> getSpeakersByTalk(Long talkId) {
		List<TalkSpeakerRelation> talkSpeakerRelations = getTalkSpeakerRelations(talkId);
		List<Speaker> result = new ArrayList<Speaker>();
		for (TalkSpeakerRelation talkSpeakerRelation : talkSpeakerRelations) {
			result.add(talkSpeakerRelation.getSpeaker());
		}
		return result;
	}

	private List<TalkSpeakerRelation> getTalkSpeakerRelations(Long talkId) {
		TypedQuery<TalkSpeakerRelation> query = em.createNamedQuery(
				"TalkSpeakerRelation.ByTalk", TalkSpeakerRelation.class);
		query.setParameter("talkId", talkId);

		List<TalkSpeakerRelation> talkSpeakerRelations = query.getResultList();
		return talkSpeakerRelations;
	}

	@Override
	public List<Talk> getTalks(Date start, Date end) {
		JPAQuery query = new JPAQuery(em);
		QTalk talk = QTalk.talk;
		query.from(talk).where(talk.start.lt(start).and(talk.end.gt(start)),
				talk.start.lt(end).and(talk.end.gt(end)),
				talk.start.gt(start).and(talk.end.lt(end)));
		return query.list(talk);
	}

	@Override
	public List<Talk> getTalksBySpeaker(Long speakerId) {
		TypedQuery<TalkSpeakerRelation> query = em.createNamedQuery(
				"TalkSpeakerRelation.BySpeaker", TalkSpeakerRelation.class);
		query.setParameter("speakerId", speakerId);

		List<TalkSpeakerRelation> talkSpeakerRelations = query.getResultList();

		List<Talk> result = new ArrayList<Talk>();
		for (TalkSpeakerRelation talkSpeakerRelation : talkSpeakerRelations) {
			result.add(talkSpeakerRelation.getTalk());
		}
		return result;
	}

	@Override
	public List<Talk> getTalksByRoom(Long roomId) {
		TypedQuery<Talk> query = em.createNamedQuery("Talk.ByRoom", Talk.class);
		query.setParameter("roomId", roomId);
		return query.getResultList();
	}

	@Override
	public Talk getTalk(Long talkId) {
		return talkId != null ? em.find(Talk.class, talkId) : null;
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
