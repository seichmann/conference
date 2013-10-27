package com.prodyna.conference.service.decorator;

import java.util.Date;
import java.util.List;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;

import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.producer.QueueSender;
import com.prodyna.conference.producer.QueueSender.MessageCreatorCallback;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.exception.ConferenceServiceException;

/**
 * Decorator for sending messages on talk modification.
 * 
 * @author Stephan Eichmann
 * 
 */
@Decorator
public class TalkServiceBeanDecorator implements TalkService {

	@Inject
	@Delegate
	private TalkService talkService;

	@Inject
	private EntityManager entityManager;

	@Inject
	private QueueSender queueSender;

	public TalkServiceBeanDecorator() {
	}

	@Override
	public List<Talk> getTalksByRoom(Long roomId) {
		return talkService.getTalksByRoom(roomId);
	}

	public List<Speaker> getSpeakersByTalk(Long talkId) {
		return talkService.getSpeakersByTalk(talkId);
	}

	public Talk getTalk(Long talkId) {
		return talkService.getTalk(talkId);
	}

	@Override
	public Talk saveTalk(Talk talk, List<Speaker> speakerList)
			throws ConferenceServiceException {
		Talk oldTalk = null;
		if (talk.getId() != null) {
			oldTalk = entityManager.find(Talk.class, talk.getId());
		}

		final Talk newTalk = talkService.saveTalk(talk, speakerList);

		if (oldTalk != null) {
			// Check updates and send Message to queue.
			if (!oldTalk.getStart().equals(newTalk.getStart())
					|| !oldTalk.getEnd().equals(newTalk.getEnd())) {
				queueSender.sendToTalkQueue(new MessageCreatorCallback() {

					@Override
					public Message createMessage(Session session)
							throws JMSException {
						TextMessage message = session
								.createTextMessage("Daterange changed.");
						return message;
					}
				});
			}

			if (!(oldTalk.getRoom() == null && newTalk.getRoom() == null)) {
				if ((oldTalk.getRoom() == null && newTalk.getRoom() != null)
						|| (oldTalk.getRoom() != null && newTalk.getRoom() == null)
						|| (!oldTalk.getRoom().equals(newTalk.getRoom()))) {
					queueSender.sendToTalkQueue(new MessageCreatorCallback() {

						@Override
						public Message createMessage(Session session)
								throws JMSException {
							TextMessage message = session
									.createTextMessage("Room changed.");
							return message;
						}
					});
				}
			}
		}

		return newTalk;
	}

	@Override
	public List<Talk> getTalksBySpeaker(Long speakerId) {
		return talkService.getTalksBySpeaker(speakerId);
	}

	@Override
	public List<Talk> getAllTalks() {
		return talkService.getAllTalks();
	}

	@Override
	public void deleteTalk(Talk talk) {
		talkService.deleteTalk(talk);
	}

	@Override
	public List<Talk> getTalks(Date start, Date end) {
		return talkService.getTalks(start, end);
	}
}