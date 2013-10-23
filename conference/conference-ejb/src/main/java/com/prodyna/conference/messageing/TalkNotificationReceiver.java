package com.prodyna.conference.messageing;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;

import com.prodyna.conference.event.TalkChanged;
import com.prodyna.conference.producer.QueueSender;

/**
 * Message-Driven Bean implementation class for: TalkNotificationReceiver
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = QueueSender.QUEUE_TALK),
		@ActivationConfigProperty(propertyName = "reconnectAttempts", propertyValue = "-1") })
public class TalkNotificationReceiver implements MessageListener {

	@Inject
	private Logger logger;

	@Inject
	@TalkChanged
	private Event<String> talkNotificationEvent;

	/**
	 * Default constructor.
	 */
	public TalkNotificationReceiver() {
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;

		try {
			logger.info("Received Message: " + textMessage.getText());

			talkNotificationEvent.fire(textMessage.getText());
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
