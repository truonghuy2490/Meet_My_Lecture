package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRequestServiceImpl implements MeetingRequestService {
    @Autowired
    MeetingRequestRepository meetingRequestRepository;
    @Override
    public List<MeetingRequestDTO> getAllRequest() {

        List<MeetingRequest> meetingRequests = meetingRequestRepository.findAll();
        return meetingRequests.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }
    private MeetingRequestDTO mapToDTO(MeetingRequest meetingRequest){
        MeetingRequestDTO meetingRequestDTO = new MeetingRequestDTO();
        meetingRequestDTO.setRequestId(meetingRequest.getRequestId());
        meetingRequestDTO.setRequestContent(meetingRequest.getRequestContent());
        meetingRequestDTO.setRequestStatus(meetingRequest.getRequestStatus());
        return meetingRequestDTO;
    }
    private MeetingRequest mapToEntity(MeetingRequestDTO meetingRequestDTO){
        MeetingRequest meetingRequest = new MeetingRequest();
        meetingRequestDTO.setRequestId(meetingRequest.getRequestId());
        meetingRequestDTO.setRequestContent(meetingRequest.getRequestContent());
        meetingRequestDTO.setRequestStatus(meetingRequest.getRequestStatus());

        return meetingRequest;
    }
}
