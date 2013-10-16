package com.prodyna.conference.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

//@Entity
//@XmlRootElement
//@Table(name = "talk_speaker")
//@NamedQueries({ @NamedQuery(name = "TalkSpeakerRelation.ByTalkId", query = "SELECT t FROM TalkSpeakerRelation t where t.talk.id = :talk_id") })
@Deprecated
public class TalkSpeakerRelation implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Talk talk;

	@OneToOne
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
