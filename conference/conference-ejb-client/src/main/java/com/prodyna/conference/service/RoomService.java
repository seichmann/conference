package com.prodyna.conference.service;

import java.util.List;

import com.prodyna.conference.model.Room;

public interface RoomService {

	List<Room> getAllRooms();

	void deleteRoom(Room Room);

	Room saveRoom(Room Room);
}
