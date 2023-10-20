package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.impl.MeetingRequestServiceImpl;
import com.springboot.meetMyLecturer.utils.AppConstants;
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

    //DONE
    @PutMapping("/{requestId}/subject/{subjectId}")
    public ResponseEntity<MeetingRequestResponseDTO> updateRequestMeeting(@PathVariable Long requestId,
                                                                          @PathVariable String subjectId,
                                                                          @RequestParam String requestContent)
    {
        MeetingRequestResponseDTO meetingRequestResponseDTO = meetingRequestService.updateRequest(requestContent, subjectId, requestId);

        return new ResponseEntity<>(meetingRequestResponseDTO,HttpStatus.OK);
    }

    //DONE
    @PostMapping("student/{studentId}/lecturer/{lecturerId}/subject/{subjectId}")
    public ResponseEntity<MeetingRequestResponseDTO> createRequest(@PathVariable Long studentId,
                                                                   @PathVariable Long lecturerId,
                                                                   @PathVariable String subjectId
            , @RequestBody MeetingRequestDTO meetingRequestDTO){
            MeetingRequestResponseDTO meetingRequestResponseDTO = meetingRequestService.createRequest(studentId,lecturerId,subjectId,meetingRequestDTO);
        return new ResponseEntity<>(meetingRequestResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MeetingRequestResponseDTO>> getAllRequestByUserId(@PathVariable Long userId){
        List<MeetingRequestResponseDTO> meetingRequestResponseDTOList = meetingRequestService.getAllRequestByUserId(userId);
        return new ResponseEntity<>(meetingRequestResponseDTOList,HttpStatus.FOUND);
    }


    //DONE
    @DeleteMapping("/{requestId}/student/{studentId}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long requestId, @PathVariable Long studentId){
        String result = meetingRequestService.deleteRequest(requestId, studentId);
                return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("{requestId}/lecturer")
    public ResponseEntity<MeetingRequestResponseDTO> processRequest(
            @RequestBody MeetingRequest meetingRequest,
            @PathVariable Long requestId)
    {
        MeetingRequestResponseDTO meetingRequestResponseDTO = meetingRequestService.processRequest(meetingRequest, requestId);
        return new ResponseEntity<>(meetingRequestResponseDTO, HttpStatus.OK);
    }

    @GetMapping("lecturer/{lecturerId}")
    public List<MeetingRequestResponseDTO> getAllRequestByLecturerId(
            @PathVariable Long lecturerId
    ){
        List<MeetingRequestResponseDTO> requestDTOList = meetingRequestService.getRequestByUserId(lecturerId);
        return requestDTOList;
    }
}
