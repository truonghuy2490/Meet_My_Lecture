package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.Room;
import com.springboot.meetMyLecturer.modelDTO.RoomDTO;

import java.util.List;

public interface RoomService {

    List<Room> getAllRooms();

    List<Room> getAllRoomsForAdmin();

    Room createRoomForAmin(RoomDTO roomDTO);

    Room editRoomForAdmin(Room roomDTO);

}