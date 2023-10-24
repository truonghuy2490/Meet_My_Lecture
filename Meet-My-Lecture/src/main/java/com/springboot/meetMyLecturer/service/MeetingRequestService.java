package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestForStudentDTO;

import java.util.List;

public interface MeetingRequestService {
    List<MeetingRequestResponseDTO> getAllRequestByStudentId(Long userId);
    
    MeetingRequestResponseDTO updateRequest(String requestContent,Long studentId, String subjectId, Long id);
//    RequestResponse getAllRequests(int pageNo, int pageSize, String sortBy, String sortDir);

    MeetingRequestResponseDTO createRequest(Long studentId, MeetingRequestForStudentDTO meetingRequestDTO);

    String deleteRequest(Long requestId, Long studentId);

    MeetingRequestResponseDTO processRequest(MeetingRequestDTO meetingRequestDTO, Long requestId, Long lecturerId);

    List<MeetingRequestResponseDTO> getRequestByLecturerId(Long lecturerId);
}
