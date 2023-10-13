package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MeetingRequestService {
    List<MeetingRequestDTO> getAllRequest();
    MeetingRequestDTO updateRequest(MeetingRequestDTO meetingRequestDTO, Long id);
}
