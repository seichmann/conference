package com.prodyna.conference.event;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;


@ApplicationScoped
public class TalkNotificationObserver {

	@Inject
	private Logger logger;

	public void observe(@Observes @TalkChanged String event) {
		logger.info("Observed Message: " + event);
	}
}
