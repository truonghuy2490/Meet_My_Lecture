package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slots")
public class EmptySlotController {

    @Autowired
    UserService userService;
    @Autowired
    EmptySlotService slotService;



    @GetMapping("")
    public List<EmptySlotDTO> getAllEmptySlot(){
        return slotService.getAllEmptySlot();
    }

    @GetMapping("lecturer/{lecturerId}")
    public List<EmptySlotDTO> getAllEmptySlotByUserId(
            @PathVariable Long lecturerId
    ){
        return slotService.getAllEmptySlotByUserId(lecturerId);
    }

    @PostMapping("lecturer/{lecturerId}")
    public ResponseEntity<EmptySlotDTO> createEmptySlot(
            @PathVariable Long lecturerId,
            @RequestBody EmptySlot emptySlot
    ) {

        EmptySlotDTO emptySlotDTO = slotService.creatEmptySlot(lecturerId, emptySlot);

        return ResponseEntity.ok(emptySlotDTO);
    }

    // SLOT : SET STUDENT AFTER ASSIGN
    // request id, slot id
    @PutMapping("lecture/meeting-request/{requestId}/slot/{slotId}")
    public ResponseEntity<EmptySlotDTO> assignRequestToEmptySlot(
            @PathVariable Long requestId,
            @PathVariable Long slotId
    ){
        EmptySlotDTO emptySlotDTO = slotService.assignRequestToSlot(requestId, slotId);
        return new ResponseEntity<>(emptySlotDTO, HttpStatus.OK);
    }

}
