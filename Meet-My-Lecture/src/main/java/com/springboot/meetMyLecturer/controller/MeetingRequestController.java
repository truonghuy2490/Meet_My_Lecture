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

    @PutMapping("/{requestId}/subject/{subjectId}")
    public ResponseEntity<MeetingRequestDTO> updateRequestMeeting(@PathVariable Long requestId,
                                                                  @PathVariable String subjectId,
                                                                  @RequestBody MeetingRequest meetingRequest)
    {
        MeetingRequestDTO meetingRequestDTO = meetingRequestService.updateRequest(meetingRequest, subjectId, requestId);

        return new ResponseEntity<>(meetingRequestDTO,HttpStatus.OK);
    }

    @PostMapping("student/{studentId}/lecturer/{lecturerId}/subject/{subjectId}")
    public ResponseEntity<MeetingRequestDTO> createRequest(@PathVariable Long studentId,
                                                           @PathVariable Long lecturerId,
                                                           @PathVariable String subjectId
            ,@RequestBody MeetingRequest meetingRequest){
            MeetingRequestDTO meetingRequestDTO = meetingRequestService.createRequest(studentId,lecturerId,subjectId,meetingRequest);
        return new ResponseEntity<>(meetingRequestDTO,HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MeetingRequestDTO>> getAllRequestByUserId(@PathVariable Long userId){
        List<MeetingRequestDTO> meetingRequestDTOList = meetingRequestService.getAllRequestByUserId(userId);
        return new ResponseEntity<>(meetingRequestDTOList,HttpStatus.FOUND);
    }


    @DeleteMapping("/{requestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long requestId){
        String result = meetingRequestService.deleteRequest(requestId);
                return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("lecturer/{requestId}")
    public ResponseEntity<MeetingRequestDTO> processRequest(
            @RequestBody MeetingRequest meetingRequest,
            @PathVariable Long requestId)
    {
        MeetingRequestDTO meetingRequestDTO = meetingRequestService.processRequest(meetingRequest, requestId);
        return new ResponseEntity<>(meetingRequestDTO, HttpStatus.OK);
    }

    @GetMapping("lecturer/{lecturerId}")
    public List<MeetingRequestDTO> getAllRequestByLecturerId(
            @PathVariable Long lecturerId
    ){
        List<MeetingRequestDTO> requestDTOList = meetingRequestService.getRequestByUserId(lecturerId);
        return requestDTOList;
    }
}
