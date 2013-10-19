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

import com.prodyna.conference.model.Talk;
import com.prodyna.conference.producer.QueueSender;
import com.prodyna.conference.producer.QueueSender.MessageCreatorCallback;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.exception.ConferenceServiceException;

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
	public Talk saveTalk(Talk talk) throws ConferenceServiceException {
		Talk oldTalk = null;
		if (talk.getId() != null) {
			oldTalk = entityManager.find(Talk.class, talk.getId());
		}

		final Talk newTalk = talkService.saveTalk(talk);

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

	public List<Talk> getAllTalks() {
		return talkService.getAllTalks();
	}

	public void deleteTalk(Talk talk) {
		talkService.deleteTalk(talk);
	}

	public Talk loadTalkEager(Talk talk) {
		return talkService.loadTalkEager(talk);
	}

	public List<Talk> getTalks(Date start, Date end) {
		return talkService.getTalks(start, end);
	}

}
