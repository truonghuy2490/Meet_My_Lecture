package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.service.impl.MeetingRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
public class MeetingRequestController {
    @Autowired
    MeetingRequestServiceImpl meetingRequestService;

    @PutMapping
    public ResponseEntity<MeetingRequestDTO> updateRequestMeeting(){
        return null;
    }

    @PostMapping("student/{studentId}/lecturer/{lecturerId}")
    public ResponseEntity<MeetingRequestDTO> createRequest(@PathVariable int studentId,
                                                           @PathVariable int lecturerId
            ,@RequestBody MeetingRequest meetingRequest){
            MeetingRequestDTO meetingRequestDTO = meetingRequestService.createRequest(studentId,lecturerId,meetingRequest);
        return new ResponseEntity<>(meetingRequestDTO,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MeetingRequestDTO>> getAllRequest(){
        List<MeetingRequestDTO> meetingRequestDTOList = meetingRequestService.getAllRequest();
        return new ResponseEntity<>(meetingRequestDTOList,HttpStatus.FOUND);
    }
}
