package com.prodyna.conference.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.conference.interceptor.Monitored;
import com.prodyna.conference.model.Room;

@Stateless
@Monitored
public class RoomServiceBean implements RoomService {

	@Inject
	private EntityManager em;

	@Override
	public List<Room> getAllRooms() {
		return em.createNamedQuery("Room.All", Room.class).getResultList();
	}

	@Override
	public Room saveRoom(Room Room) {
		return em.merge(Room);
	}

	@Override
	public void deleteRoom(Room Room) {
		Room loadedRoom = em.find(Room.class, Room.getId());

		if (loadedRoom != null) {
			em.remove(loadedRoom);
		}
	}
}
