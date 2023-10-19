package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MeetingRequestService {
    List<MeetingRequestDTO> getAllRequestByUserId(Long userId);

    List<MeetingRequestDTO> getAllRequests();
    MeetingRequestDTO updateRequest(String requestContent, String subjectId, Long id);

    MeetingRequestDTO createRequest(Long studentId, Long lecturerId, String subjectId,String requestContent);

    String deleteRequest(Long requestId, Long studentId);

    MeetingRequestDTO processRequest(MeetingRequest meetingRequest, Long requestId);

    List<MeetingRequestDTO> getRequestByUserId(Long lecturerId);
}
