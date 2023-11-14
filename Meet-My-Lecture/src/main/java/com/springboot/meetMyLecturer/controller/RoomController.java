package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.constant.PageConstant;
import com.springboot.meetMyLecturer.entity.Room;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RoomResponse;
import com.springboot.meetMyLecturer.modelDTO.RoomDTO;
import com.springboot.meetMyLecturer.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room/admin")
public class RoomController {

    @Autowired
    RoomService roomService;

    //DONE-DONE
    @GetMapping("rooms")
    public RoomResponse getAllRooms(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "roomId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "status", defaultValue = "", required = false) String status
    ){
        return roomService.getAllRoom(pageNo, pageSize, sortBy, sortDir, status);
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
