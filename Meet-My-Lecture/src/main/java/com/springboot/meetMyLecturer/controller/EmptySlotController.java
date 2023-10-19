package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.modelDTO.BookedSlotHomePageDTO;
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



//    @GetMapping("")
//    public List<EmptySlotDTO> getAllEmptySlot(){
//        return slotService.getAllEmptySlot();
//    }

    @GetMapping("lecturer/{lecturerId}")
    public List<BookedSlotHomePageDTO> getAllEmptySlotByUserId(
            @PathVariable Long lecturerId
    ){
        return slotService.getAllEmptySlotByUserId(lecturerId);
    }

    @PostMapping("lecturer/{lecturerId}")
    public ResponseEntity<BookedSlotHomePageDTO> createEmptySlot(
            @PathVariable Long lecturerId,
            @RequestBody EmptySlot emptySlot
    ) {

        BookedSlotHomePageDTO bookedSlotHomePageDTO = slotService.creatEmptySlot(lecturerId, emptySlot);

        return ResponseEntity.ok(bookedSlotHomePageDTO);
    }

    // SLOT : SET STUDENT AFTER ASSIGN
    // request id, slot id
    @PutMapping("lecture/meeting-request/{requestId}/slot/{slotId}")
    public ResponseEntity<BookedSlotHomePageDTO> assignRequestToEmptySlot(
            @PathVariable Long requestId,
            @PathVariable Long slotId
    ){
        BookedSlotHomePageDTO bookedSlotHomePageDTO = slotService.assignRequestToSlot(requestId, slotId);
        return new ResponseEntity<>(bookedSlotHomePageDTO, HttpStatus.OK);
    }

}
