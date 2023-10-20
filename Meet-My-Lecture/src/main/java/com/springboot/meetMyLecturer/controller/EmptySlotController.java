package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
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

    // get Slot and Sort by Attribute "asc" or "desc"
    @GetMapping
    public SlotResponse getAllSlots(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
//        return slotService.getAllSlots(pageNo,pageSize,sortBy,sortDir);
        return null;
    }

    @GetMapping("lecturer/{lecturerId}")
    public List<BookedSlotHomePageDTO> getAllEmptySlotByUserId(
            @PathVariable Long lecturerId
    ){
        return slotService.getAllEmptySlotByUserId(lecturerId);
    }

    //DONE
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
