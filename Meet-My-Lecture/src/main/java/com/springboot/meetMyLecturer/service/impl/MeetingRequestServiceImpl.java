package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.springframework.stereotype.Service;

@Service
public class MeetingRequestServiceImpl implements MeetingRequestService {
    private MeetingRequestService meetingRequestService;

    public MeetingRequestServiceImpl(MeetingRequestService meetingRequestService) {
        this.meetingRequestService = meetingRequestService;
    }
}
