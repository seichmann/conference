package com.prodyna.conference.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.model.Room;
import com.prodyna.conference.model.Speaker;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.service.RoomService;
import com.prodyna.conference.service.SpeakerService;
import com.prodyna.conference.service.TalkService;
import com.prodyna.conference.util.DateUtil;

public class ConferenceServiceTest extends AbstractDeployableTest {

	@Inject
	private ConferenceService service;

	@Inject
	private TalkService talkService;

	@Inject
	private RoomService roomService;

	@Inject
	private SpeakerService speakerService;

	@Test
	@InSequence(value = 1)
	public void createEmpty() throws Exception {
		Conference conference = new Conference();
		conference.setName("JAX");
		conference.setDescription("ENTER");
		conference.setStart(new Date());
		conference.setEnd(new Date());

		Conference saveConference = service.saveConference(conference);

		List<Conference> conferences = service.getAllConferences();
		Assert.assertEquals(1, conferences.size());

		service.deleteConference(saveConference);

		List<Conference> conferences2 = service.getAllConferences();
		Assert.assertEquals(0, conferences2.size());
	}

	@Test
	@InSequence(value = 2)
	public void createComplex() throws Exception {
		Conference conference = new Conference();
		conference.setName("Prodyna Hausmesser");
		conference.setDescription("Best of the best.");
		conference.setStart(DateUtil.parse("01.05.2015"));
		conference.setEnd(DateUtil.parse("01.06.2015"));

		Room room1 = new Room();
		room1.setName("Room 1");
		room1.setCapacity(20);
		Room saveRoom1 = roomService.saveRoom(room1);

		Room room2 = new Room();
		room2.setName("Room 2");
		room2.setCapacity(50);
		Room saveRoom2 = roomService.saveRoom(room2);

		// Assert Crud Room
		List<Room> rooms = roomService.getAllRooms();
		Assert.assertEquals(2, rooms.size());
		Assert.assertEquals("Room 1", rooms.get(0).getName());
		Assert.assertEquals("Room 2", rooms.get(1).getName());

		Speaker speaker1 = new Speaker();
		speaker1.setName("Stephan");
		speaker1.setDescription("...");

		Speaker saveSpeaker1 = speakerService.saveSpeaker(speaker1);

		Speaker speaker2 = new Speaker();
		speaker2.setName("Darko");
		speaker2.setDescription("...");

		Speaker saveSpeaker2 = speakerService.saveSpeaker(speaker2);

		// Assert Crud Speakers
		List<Speaker> speakers = speakerService.getAllSpeakers();
		Assert.assertEquals(2, speakers.size());
		Assert.assertEquals("Stephan", speakers.get(0).getName());
		Assert.assertEquals("Darko", speakers.get(1).getName());

		Talk talk1 = new Talk();
		talk1.setName("Java EE6");
		talk1.setDescription("JPA, CDI, EJB");
		talk1.setStart(DateUtil.parse("01.05.2015 12:30"));
		talk1.setEnd(DateUtil.parse("01.05.2015 14:30"));
		talk1.setRoom(saveRoom1);
		Set<Speaker> speakers1 = new HashSet<Speaker>();
		speakers1.add(saveSpeaker1);
		speakers1.add(saveSpeaker2);
		talk1.setSpeakers(speakers1);
		Talk saveTalk1 = talkService.saveTalk(talk1);

		Talk talk2 = new Talk();
		talk2.setName("Spring");
		talk2.setDescription("Ecosystem");
		talk2.setStart(DateUtil.parse("01.06.2015 12:30"));
		talk2.setEnd(DateUtil.parse("01.06.2015 14:30"));
		talk2.setRoom(saveRoom2);
		Set<Speaker> speakers2 = new HashSet<Speaker>();
		speakers2.add(saveSpeaker1);
		talk2.setSpeakers(speakers2);
		Talk saveTalk2 = talkService.saveTalk(talk2);

		// Assert Crud Talks
		List<Talk> loadedTalks = talkService.getAllTalks();
		Assert.assertEquals(2, loadedTalks.size());
		Talk loadedTalk1 = loadedTalks.get(0);
		Assert.assertEquals("Java EE6", loadedTalk1.getName());
		Assert.assertNotNull(loadedTalk1.getRoom());
		Assert.assertEquals("Room 1", loadedTalk1.getRoom().getName());
		Assert.assertEquals(2, loadedTalk1.getSpeakers().size());

		Talk loadedTalk2 = loadedTalks.get(1);
		Assert.assertEquals("Spring", loadedTalk2.getName());
		Assert.assertNotNull(loadedTalk2.getRoom());
		Assert.assertEquals("Room 2", loadedTalk2.getRoom().getName());
		Assert.assertEquals(1, loadedTalk2.getSpeakers().size());

		List<Talk> talks2 = new ArrayList<Talk>();
		talks2.add(saveTalk1);
		talks2.add(saveTalk2);
		conference.setTalks(talks2);
		service.saveConference(conference);

		// Assert Crud Conferece
		List<Conference> conferences = service.getAllConferences();
		Assert.assertEquals(1, conferences.size());
		Assert.assertEquals(2, conferences.get(0).getTalks().size());

		Talk loadedTalkInConference = conferences.get(0).getTalks().get(0);
		Assert.assertNotNull(loadedTalkInConference.getId());
		Assert.assertNotNull(loadedTalkInConference.getRoom());
	}
}
