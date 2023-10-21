package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.LecturerService;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/lecturer")
public class LecturerController {



    @Autowired
    MeetingRequestService meetingRequestService;

    @Autowired
    EmptySlotService emptySlotService;

    @Autowired
    LecturerService lecturerService;

    @Autowired
    UserService userService;

    // REQUESTS : GET ALL MEETING REQUESTS
    /*@GetMapping("requests")
    public List<MeetingRequestDTO> getAllRequestsMeeting (){
        return meetingRequestService.getAllRequest();
    }*/

    @PostMapping("/{lecturerId}/subject/{subjectId}")
    public ResponseEntity<LecturerSubjectResponseDTO> insertTaughtSubjects(@PathVariable Long lecturerId,
                                                                           @PathVariable String subjectId){
        LecturerSubjectResponseDTO lecturerSubjectResponseDTO = lecturerService.insertTaughtSubjects(lecturerId, subjectId);

        return  new ResponseEntity<>(lecturerSubjectResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{lecturerId}/subject/{subjectId}")
    public ResponseEntity<String> deleteSubjectsForLecturer(@PathVariable Long lecturerId,
                                                                     @PathVariable String subjectId){

        String result = lecturerService.deleteSubjectsForLecturer(lecturerId, subjectId);

        return  new ResponseEntity<>(result, HttpStatus.OK);
    }


}
