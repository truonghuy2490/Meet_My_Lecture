package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;

import java.util.List;

public interface MeetingRequestService {
    List<MeetingRequestDTO> getAllRequest();
}
