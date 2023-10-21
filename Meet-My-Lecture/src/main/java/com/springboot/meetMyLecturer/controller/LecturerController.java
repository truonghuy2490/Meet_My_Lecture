package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserService userService;

    // REQUESTS : GET ALL MEETING REQUESTS
    /*@GetMapping("requests")
    public List<MeetingRequestDTO> getAllRequestsMeeting (){
        return meetingRequestService.getAllRequest();
    }*/

    @PutMapping("/{lecturerId}/subject/{subjectId}")
    public ResponseEntity<LecturerSubjectResponseDTO> updateSubjects(@PathVariable Long lecturerId,
                                                                     @PathVariable String subjectId){

return null;
    }


}
