package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.Room;
import com.springboot.meetMyLecturer.modelDTO.RoomDTO;
import com.springboot.meetMyLecturer.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/room/admin")
public class RoomController {

    @Autowired
    RoomService roomService;

    //DONE-DONE
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
            List<Room> roomList = roomService.getAllRoomsForAdmin();
            return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    //DONE-DONE
    @PostMapping
    public ResponseEntity<Room> createRoomForAmin(@RequestBody RoomDTO roomDTO){
        Room room = roomService.createRoomForAmin(roomDTO);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    //DONE-DONE
    @PutMapping
    public ResponseEntity<Room> editRoomForAdmin(@RequestBody Room roomDTO){
        Room room = roomService.editRoomForAdmin(roomDTO);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
