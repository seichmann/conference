package com.prodyna.conference.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.prodyna.conference.model.Room;
import com.prodyna.conference.service.RoomService;

@Named(value = "roomController")
@SessionScoped
public class RoomController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7079315195760451446L;

	@EJB
	private RoomService roomService;

	private Room editRoom;

	public RoomController() {
		createNew();
	}

	public List<Room> getList() {
		try {
			return roomService.getAllRooms();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
			return new ArrayList<Room>();
		}
	}

	public void save() {
		try {
			roomService.saveRoom(editRoom);
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void delete() {
		try {
			roomService.deleteRoom(editRoom);

			createNew();
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void onRowSelect(SelectEvent event) {
		editRoom = (Room) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		createNew();
	}

	public void createNew() {
		editRoom = new Room();
	}

	// Getter / Setter
	public Room getEditRoom() {
		return editRoom;
	}

	public void setEditRoom(Room editRoom) {
		this.editRoom = editRoom;
	}
}
