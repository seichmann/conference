package com.prodyna.conference.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "talk_speaker")
@NamedQueries({
		@NamedQuery(name = "TalkSpeakerRelation.ByTalk", query = "SELECT t FROM TalkSpeakerRelation t where t.talk.id = :talkId"),
		@NamedQuery(name = "TalkSpeakerRelation.BySpeaker", query = "SELECT t from TalkSpeakerRelation t where t.speaker.id = :speakerId"),
		@NamedQuery(name = "TalkSpeakerRelation.ByBusinessKey", query = "SELECT t from TalkSpeakerRelation t where t.speaker.id = :speakerId and t.talk.id = :talkId"), })
public class TalkSpeakerRelation implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Talk talk;

	@ManyToOne
	private Speaker speaker;

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}
}
