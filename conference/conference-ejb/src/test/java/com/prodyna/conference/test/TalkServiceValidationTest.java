package com.prodyna.conference.test;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import com.prodyna.conference.model.Room;
import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.RoomService;
import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.service.exception.RoomConstraintException;
import com.prodyna.conference.service.exception.SpeakerConstraintException;
import com.prodyna.conference.util.DateUtil;

public class TalkServiceValidationTest extends AbstractDeployableTest {

	@Inject
	private TalkService talkService;

	@Inject
	private RoomService roomService;

	@Inject
	private SpeakerService speakerService;

	@Test(expected = SpeakerConstraintException.class)
	@InSequence(value = 1)
	public void valdiateSpeakerConstraint() throws Exception {
		Speaker speaker = new Speaker();
		speaker.setName("Stephan");
		speaker.setDescription("...");

		Speaker saveSpeaker = speakerService.saveSpeaker(speaker);

		Talk talk1 = new Talk();
		talk1.setName("Java EE6");
		talk1.setDescription("JPA, CDI, EJB");
		talk1.setStart(DateUtil.parseHourMinute("01.05.2013 12:30"));
		talk1.setEnd(DateUtil.parseHourMinute("01.05.2013 14:30"));
		Set<Speaker> speakers = new HashSet<Speaker>();
		speakers.add(saveSpeaker);
		talk1.setSpeakers(speakers);
		Talk saveTalk1 = talkService.saveTalk(talk1);

		Talk talk2 = new Talk();
		talk2.setName("Spring");
		talk2.setDescription("Ecosystem");
		talk2.setStart(DateUtil.parseHourMinute("01.05.2013 13:30"));
		talk2.setEnd(DateUtil.parseHourMinute("01.05.2013 15:30"));
		talk2.setSpeakers(speakers);
		Talk saveTalk2 = talkService.saveTalk(talk2);
	}

	@Test(expected = RoomConstraintException.class)
	@InSequence(value = 1)
	public void valdiateRoomConstraint() throws Exception {
		Room room = new Room();
		room.setName("Meeting 1");
		room.setCapacity(10);

		Room saveRoom = roomService.saveRoom(room);

		Talk talk1 = new Talk();
		talk1.setName("Java EE6 2");
		talk1.setDescription("JPA, CDI, EJB");
		talk1.setStart(DateUtil.parseHourMinute("01.05.2013 10:30"));
		talk1.setEnd(DateUtil.parseHourMinute("01.05.2013 12:30"));
		talk1.setRoom(saveRoom);
		Talk saveTalk1 = talkService.saveTalk(talk1);

		Talk talk2 = new Talk();
		talk2.setName("Spring 2");
		talk2.setDescription("Ecosystem");
		talk2.setStart(DateUtil.parseHourMinute("01.05.2013 11:30"));
		talk2.setEnd(DateUtil.parseHourMinute("01.05.2013 15:30"));
		talk2.setRoom(saveRoom);
		Talk saveTalk2 = talkService.saveTalk(talk2);
	}
}
