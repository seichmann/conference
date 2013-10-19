package com.prodyna.conference.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.slf4j.Logger;

@ApplicationScoped
public class QueueSender {

	public static final String QUEUE_TALK = "queue/talk";

	public interface MessageCreatorCallback {
		public Message createMessage(Session session) throws JMSException;
	}

	@Inject
	private Logger logger;

	public void sendToTalkQueue(MessageCreatorCallback callback) {
		try {
			InitialContext init = new InitialContext();
			// get reference to the ConnectionFactory
			ConnectionFactory connectionFactory = (ConnectionFactory) init
					.lookup("ConnectionFactory");

			Connection connection = connectionFactory.createConnection();
			connection.start();
			// get reference to JMS destination
			javax.jms.Queue destination = (javax.jms.Queue) init
					.lookup(QUEUE_TALK);
			Session session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(destination);

			producer.send(callback.createMessage(session));
			logger.info("Message send to TalkQueue!");

			session.commit();
			producer.close();
			session.close();
			connection.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
