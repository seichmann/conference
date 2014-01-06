package com.prodyna.conference.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Projections;
import com.mysema.query.types.expr.Wildcard;
import com.prodyna.conference.bean.RoomCountResult;
import com.prodyna.conference.interceptor.Monitored;
import com.prodyna.conference.model.Conference;
import com.prodyna.conference.model.QConference;
import com.prodyna.conference.model.QRoom;
import com.prodyna.conference.model.QTalk;
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

	@Override
	public List<RoomCountResult> getRoomsCount(Conference conference) {
		JPAQuery query = new JPAQuery(em);

		QConference c = QConference.conference;
		QTalk talkAlias = QTalk.talk;
		QRoom room = talkAlias.room;

		// Alternative: directly to map
		// Map<Room, Long> list = query.from(c).join(c.talks, talkAlias)
		// .join(room).groupBy(room).orderBy(Wildcard.count.desc())
		// .map(room, Wildcard.count);
		return query
				.from(c)
				.join(c.talks, talkAlias)
				.join(room)
				.groupBy(room)
				.orderBy(Wildcard.count.desc())
				.list(Projections.constructor(RoomCountResult.class, room,
						Wildcard.count));
	}
}
