package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.model.Room;

/**
 * Management interface for CRUD-Operations on {@link Room}.
 * 
 * @author Stephan Eichmann
 * 
 */
public interface RoomService {

	/**
	 * Returns all Rooms.
	 * 
	 * @return
	 */
	List<Room> getAllRooms();

	/**
	 * Delete given room.
	 * 
	 * @param Room
	 */
	void deleteRoom(Room Room);

	/**
	 * Create / Update given room.
	 * 
	 * @param Room
	 * @return
	 */
	Room saveRoom(Room Room);
}
