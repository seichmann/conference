package com.prodyna.conference.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@XmlRootElement
@Table(name = "talk")
@NamedQueries({
		@NamedQuery(name = "Talk.All", query = "SELECT t from Talk t"),
		@NamedQuery(name = "Talk.ByDates", query = "SELECT t from Talk t where (t.start < :start AND t.end > :start) OR (t.start < :end AND t.end > :end) OR (t.start > :start AND t.end < :end)") })
public class Talk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5246534542753142082L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String name;

	private String description;

	@Future
	@NotNull
	private Date start;

	@Future
	@NotNull
	private Date end;

	@Transient
	private long duration;

	@ManyToOne(fetch = FetchType.EAGER)
	private Room room;

	@ManyToMany(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<Speaker> speakers;

	@PostLoad
	public void initSpeakerCollectionSize() {
		speakers.size();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDuration() {
		return Math
				.round(Math.ceil((end.getTime() - start.getTime()) / 1000 * 60));
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Set<Speaker> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(Set<Speaker> speakers) {
		this.speakers = speakers;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Talk other = (Talk) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Talk [id=" + id + ", name=" + name + ", description="
				+ description + ", start=" + start + ", end=" + end
				+ ", duration=" + duration + "]";
	}
}
