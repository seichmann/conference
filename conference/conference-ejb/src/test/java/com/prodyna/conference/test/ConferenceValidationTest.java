package com.prodyna.conference.test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.exception.ConferenceConstraintException;
import com.prodyna.conference.util.DateUtil;

/**
 * Test for Validation Logic of Conference.
 * 
 * @author Stephan Eichmann
 * 
 */
public class ConferenceValidationTest extends AbstractDeployableTest {

	@Inject
	private TalkService talkService;

	@Inject
	private ConferenceService conferenceService;

	@Test(expected = ConferenceConstraintException.class)
	@InSequence(value = 1)
	public void valdiateSpeakerConstraint() throws Exception {
		Talk talk1 = new Talk();
		talk1.setName("Java EE6");
		talk1.setDescription("JPA, CDI, EJB");
		talk1.setStart(DateUtil.parseHourMinute("01.05.2015 12:30"));
		talk1.setEnd(DateUtil.parseHourMinute("01.05.2015 14:30"));
		Talk saveTalk1 = talkService.saveTalk(talk1);

		Conference conference = new Conference();
		conference.setName("Prodyna Hausmesser");
		conference.setDescription("Best of the best.");
		conference.setStart(DateUtil.parse("01.07.2015"));
		conference.setEnd(DateUtil.parse("01.07.2015"));
		List<Talk> talks = new ArrayList<Talk>();
		talks.add(saveTalk1);
		conference.setTalks(talks);
		conferenceService.saveConference(conference);
	}
}
