package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.Room;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.RoomService;
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
    @Autowired
    RoomService roomService;

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

    //DONE - DONE
    @GetMapping("lecturer/room")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> roomList = roomService.getAllRooms();
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("subjects/lecturer/{lecturerId}")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjectsOfLecturer(@PathVariable Long lecturerId){
        List<SubjectResponseDTO> responseDTOList = slotService.getSubjectsOfLecturer(lecturerId);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }
    @PutMapping("{slotId}/lecturer/{lectureId}")
    public ResponseEntity<EmptySlotResponseDTO> rescheduleEmptySlot(
            @RequestBody EmptySlotResponseDTO emptySlotResponseDTO,
            @PathVariable Long lectureId,
            @PathVariable Long slotId

    ){
        EmptySlotResponseDTO responseDTO = slotService.rescheduleEmptySlot(lectureId, slotId, emptySlotResponseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
