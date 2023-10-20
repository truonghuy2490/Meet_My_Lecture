package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RequestResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MeetingRequestService {
    List<MeetingRequestResponseDTO> getAllRequestByUserId(Long userId);

    List<MeetingRequestResponseDTO> getAllRequests();
    MeetingRequestResponseDTO updateRequest(String requestContent, String subjectId, Long id);
//    RequestResponse getAllRequests(int pageNo, int pageSize, String sortBy, String sortDir);

    MeetingRequestResponseDTO createRequest(Long studentId, Long lecturerId, String subjectId, MeetingRequestDTO meetingRequestDTO);

    String deleteRequest(Long requestId, Long studentId);

    MeetingRequestResponseDTO processRequest(MeetingRequest meetingRequest, Long requestId);

    List<MeetingRequestResponseDTO> getRequestByUserId(Long lecturerId);
}
