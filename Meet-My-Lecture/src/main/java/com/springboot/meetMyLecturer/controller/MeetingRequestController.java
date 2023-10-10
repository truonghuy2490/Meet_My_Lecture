package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/meeting-requests")
public class MeetingRequestController {
    private MeetingRequestService meetingRequestService;

    public MeetingRequestController(MeetingRequestService meetingRequestService) {
        this.meetingRequestService = meetingRequestService;
    }
}
