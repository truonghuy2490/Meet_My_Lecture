package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
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

    //DONE
    @PostMapping("lecturer/{lecturerId}")
    public ResponseEntity<EmptySlotResponseDTO> createEmptySlot(
            @PathVariable Long lecturerId,
            @RequestBody EmptySlotDTO emptySlotDTO
    ) {
        EmptySlotResponseDTO responseSlot = slotService.creatEmptySlot(lecturerId, emptySlotDTO);

        return ResponseEntity.ok(responseSlot);
    }

    //DONE
    @PutMapping("lecture/meeting-request/{requestId}/emptySlot/{emptySlotId}")
    public ResponseEntity<EmptySlotResponseDTO> assignRequestToEmptySlot(
            @PathVariable Long requestId,
            @PathVariable Long emptySlotId
    ){
        EmptySlotResponseDTO emptySlotResponseDTO = slotService.assignRequestToSlot(requestId, emptySlotId);
        return new ResponseEntity<>(emptySlotResponseDTO, HttpStatus.OK);
    }
}
