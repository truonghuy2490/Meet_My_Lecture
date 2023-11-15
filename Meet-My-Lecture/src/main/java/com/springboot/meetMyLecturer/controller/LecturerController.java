package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.entity.LecturerSubjectId;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.LecturerService;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/lecturer")
public class LecturerController {

    @Autowired
    MeetingRequestService meetingRequestService;

    @Autowired
    EmptySlotService emptySlotService;

    @Autowired
    LecturerService lecturerService;

    @Autowired
    UserService userService;

    //DONE-DONE
    @PostMapping("/subject")
    public ResponseEntity<List<LecturerSubjectResponseDTO>> insertTaughtSubjects(@RequestBody Set<LecturerSubjectId> lecturerSubjectId){
        List<LecturerSubjectResponseDTO> lecturerSubjectResponseDTO = lecturerService.insertTaughtSubjects(lecturerSubjectId);

        return  new ResponseEntity<>(lecturerSubjectResponseDTO, HttpStatus.CREATED);
    }

    //DONE-DONE
    @PutMapping("/subject")
    public ResponseEntity<String> deleteSubjectsForLecturer(@RequestBody LecturerSubjectId lecturerSubjectId){

        String result = lecturerService.deleteSubjectsForLecturer(lecturerSubjectId);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{lecturerId}/subjects")
    public ResponseEntity<List<LecturerSubjectResponseDTO>> getSubjects(@PathVariable Long lecturerId){

        List<LecturerSubjectResponseDTO> responseDTOList = lecturerService.getAllSubjects(lecturerId);
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }

    //DONE
    @PutMapping("{requestId}/lecturer/{lecturerId}")
    public ResponseEntity<MeetingRequestResponseDTO> processRequest(
            @RequestBody MeetingRequestDTO meetingRequestDTO,
            @PathVariable Long requestId,
            @PathVariable Long lecturerId
    )
    {
        MeetingRequestResponseDTO meetingRequestResponseDTO = meetingRequestService.processRequest(meetingRequestDTO, requestId, lecturerId);
        return new ResponseEntity<>(meetingRequestResponseDTO, HttpStatus.OK);
    }

    //DONE-DONE
    @GetMapping("/{lecturerId}")
    public ResponseEntity<List<MeetingRequestResponseDTO>> getAllRequestByLecturerId(
            @PathVariable Long lecturerId){
        List<MeetingRequestResponseDTO> requestDTOList = meetingRequestService.getRequestByLecturerId(lecturerId);
        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }

}
