package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
public class MeetingRequestController {
    @Autowired
    MeetingRequestService meetingRequestService;

    @GetMapping // GET ALL MEETING REQUESTS
    public List<MeetingRequestDTO> getAllRequestsMeeting (){
        return meetingRequestService.getAllRequest();
    }

    @PutMapping("/{id}")  // UPDATE MEETING REQUEST
    public ResponseEntity<MeetingRequestDTO> updateRequestMeeting(
            @RequestBody MeetingRequestDTO meetingRequestDTO,
            @PathVariable Long id)
    {
        MeetingRequestDTO responseRequest = meetingRequestService.updateRequest(meetingRequestDTO, id);
        return new ResponseEntity<>(responseRequest, HttpStatus.OK);
    }


}
