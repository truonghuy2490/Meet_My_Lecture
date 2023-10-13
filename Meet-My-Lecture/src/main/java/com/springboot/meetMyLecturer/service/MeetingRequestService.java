package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;

import java.util.List;

public interface MeetingRequestService {

    MeetingRequestDTO createRequest(int studentId, int lecturerId,MeetingRequest meetingRequest);

}
