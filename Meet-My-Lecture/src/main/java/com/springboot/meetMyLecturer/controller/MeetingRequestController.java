package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.constant.PageConstant;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestForStudentDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RequestResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;
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

    @GetMapping
    public RequestResponse getAllRequest(
            @RequestParam(value = "pageNo", defaultValue = PageConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageConstant.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = PageConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return meetingRequestService.getAllRequest(pageNo, pageSize, sortBy, sortDir);
    }

    //DONE
    @PutMapping("/{requestId}/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<MeetingRequestResponseDTO> updateRequestMeeting(@PathVariable Long requestId,
                                                                          @PathVariable String subjectId,
                                                                          @PathVariable Long studentId,
                                                                          @RequestParam String requestContent)
    {
        MeetingRequestResponseDTO meetingRequestResponseDTO = meetingRequestService.updateRequest(requestContent, studentId,subjectId,requestId);

        return new ResponseEntity<>(meetingRequestResponseDTO,HttpStatus.OK);
    }

    //DONE-DONE
    @PostMapping("student/{studentId}")
    public ResponseEntity<MeetingRequestResponseDTO> createRequest(@PathVariable Long studentId
                                            , @RequestBody MeetingRequestForStudentDTO meetingRequestDTO){
            MeetingRequestResponseDTO meetingRequestResponseDTO = meetingRequestService.createRequest(studentId,meetingRequestDTO);
        return new ResponseEntity<>(meetingRequestResponseDTO,HttpStatus.CREATED);
    }

    //DONE-DONE
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<MeetingRequestResponseDTO>> getAllRequestByStudentId(@PathVariable Long studentId){
        List<MeetingRequestResponseDTO> meetingRequestResponseDTOList = meetingRequestService.getAllRequestByStudentId(studentId);
        return new ResponseEntity<>(meetingRequestResponseDTOList,HttpStatus.OK);
    }


    //DONE-DONE
    @DeleteMapping("/{requestId}/student/{studentId}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long requestId, @PathVariable Long studentId){
        String result = meetingRequestService.deleteRequest(requestId, studentId);
                return new ResponseEntity<>(result,HttpStatus.OK);
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
    @GetMapping("lecturer/{lecturerId}")
    public ResponseEntity<List<MeetingRequestResponseDTO>> getAllRequestByLecturerId(
                                            @PathVariable Long lecturerId){
        List<MeetingRequestResponseDTO> requestDTOList = meetingRequestService.getRequestByLecturerId(lecturerId);
        return new ResponseEntity<>(requestDTOList, HttpStatus.OK);
    }
}
