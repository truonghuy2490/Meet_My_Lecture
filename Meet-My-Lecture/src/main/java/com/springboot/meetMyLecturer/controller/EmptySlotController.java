package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
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


    @GetMapping("lecturer/{lecturerId}")
    public List<BookedSlotHomePageDTO> getAllEmptySlotByUserId(
            @PathVariable Long lecturerId
    ){
        return slotService.getAllEmptySlotByUserId(lecturerId);
    }

    //DONE
    @PostMapping("lecturer/{lecturerId}")
    public ResponseEntity<BookedSlotCalendarDTO> createEmptySlot(
            @PathVariable Long lecturerId,
            @RequestBody EmptySlotDTO emptySlotDTO
    ) {

        BookedSlotCalendarDTO bookedSlotCalendarDTO = slotService.creatEmptySlot(lecturerId, emptySlotDTO);

        return ResponseEntity.ok(bookedSlotCalendarDTO);
    }

    // SLOT : SET STUDENT AFTER ASSIGN
    // request id, slot id
    @PutMapping("lecture/meeting-request/{requestId}/slot/{emptySlotId}")
    public ResponseEntity<BookedSlotCalendarDTO> assignRequestToEmptySlot(
            @PathVariable Long requestId,
            @PathVariable Long emptySlotId
    ){
        BookedSlotCalendarDTO bookedSlotCalendarDTO = slotService.assignRequestToSlot(requestId, emptySlotId);
        return new ResponseEntity<>(bookedSlotCalendarDTO, HttpStatus.OK);
    }

}
