package com.prodyna.conference.test;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;

import com.prodyna.conference.event.TalkChanged;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.util.DateUtil;

/**
 * Messaging Test.
 * 
 * @author Stephan Eichmann
 * 
 */
public class TalkNotificationTest extends AbstractDeployableTest {

	@Inject
	private Logger logger;

	@Inject
	private TalkService talkService;

	private static String observedText = "";

	@Test
	public void test() throws Exception {
		Talk talk1 = new Talk();
		talk1.setName("Java EE6");
		talk1.setDescription("JPA, CDI, EJB");
		talk1.setStart(DateUtil.parseHourMinute("01.05.2015 12:30"));
		talk1.setEnd(DateUtil.parseHourMinute("01.05.2015 14:30"));

		Talk saveTalk = talkService.saveTalk(talk1);

		saveTalk.setStart(DateUtil.parseHourMinute("01.05.2015 11:30"));

		talkService.saveTalk(saveTalk);

		try {
			Thread.sleep(5000);

			// Assert.assertEquals("Daterange changed.", observer.getEvent());
		} catch (InterruptedException e) {
			throw new Exception("Test failed!");
		}
	}

	public void observe(@Observes @TalkChanged String event) {
		observedText = event;
	}
}
