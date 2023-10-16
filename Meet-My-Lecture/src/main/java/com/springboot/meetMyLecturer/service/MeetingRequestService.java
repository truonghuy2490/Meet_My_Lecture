package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MeetingRequestService {
    List<MeetingRequestDTO> getAllRequestByUserId(Long userId);

    List<MeetingRequestDTO> getAllRequests();
    MeetingRequestDTO updateRequest(MeetingRequest meetingRequest, String subjectId, Long id);

    MeetingRequestDTO createRequest(Long studentId, Long lecturerId, String subjectId,MeetingRequest meetingRequest);

    String deleteRequest(Long requestId);

    MeetingRequestDTO processRequest(MeetingRequest meetingRequest, Long requestId);
}
