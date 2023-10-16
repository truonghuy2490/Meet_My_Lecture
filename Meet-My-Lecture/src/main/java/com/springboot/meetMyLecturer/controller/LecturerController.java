package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturer")
public class LecturerController {

    @GetMapping("{id}")
    public ResponseEntity<?> getSubjectByLecId(@PathVariable int id){
        return null;
    }

    @Autowired
    MeetingRequestService meetingRequestService;

    @Autowired
    EmptySlotService emptySlotService;

    @Autowired
    UserService userService;

    // REQUESTS : GET ALL MEETING REQUESTS
    @GetMapping("requests")
    public List<MeetingRequestDTO> getAllRequestsMeeting (){
        return meetingRequestService.getAllRequest();
    }

    // SLOT : GET ALL SLOT
    @GetMapping("slots")
    public List<EmptySlotDTO> getAllEmptySlot(){
        return emptySlotService.getAllEmptySlot();
    }
    @GetMapping("slots/{slotId}/users")
    public List<UserDTO> getUserByEmptySlotId(@PathVariable Long slotId){
        return userService.getUserByEmptySlotId(slotId);
    }

    // SLOT : CREATE EMPTY SLOT
    @PostMapping("slots/{userId}/users")
    public ResponseEntity<EmptySlotDTO> createEmptySlot(@PathVariable(value = "userId") Long userId,
                                                        @RequestBody EmptySlot emptySlot) {

        EmptySlotDTO emptySlotDTO = emptySlotService.creatEmptySlot(userId, emptySlot);

        return ResponseEntity.ok(emptySlotDTO);
    }
}
