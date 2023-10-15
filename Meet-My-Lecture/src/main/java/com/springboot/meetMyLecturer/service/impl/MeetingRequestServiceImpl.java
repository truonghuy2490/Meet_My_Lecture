package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNoFoundException;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRequestServiceImpl implements MeetingRequestService {
    @Autowired
    MeetingRequestRepository meetingRequestRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<MeetingRequestDTO> getAllRequest() {
        List<MeetingRequest> meetingRequests = meetingRequestRepository.findAll();
        return meetingRequests.stream().map(meetingRequest -> mapToDTO(meetingRequest)).collect(Collectors.toList());
    }

    @Override
    public MeetingRequestDTO updateRequest(MeetingRequestDTO meetingRequestDTO, long id) {

        MeetingRequest meetingRequest = meetingRequestRepository.findById(id).orElseThrow(() -> new ResourceNoFoundException("Request Meeting", "id", id));

        meetingRequest.setRequestId(meetingRequestDTO.getRequestId());
        meetingRequest.setRequestStatus(meetingRequestDTO.getRequestStatus());
        meetingRequest.setRequestContent(meetingRequestDTO.getRequestContent());

        MeetingRequest updateRequestMeeting = meetingRequestRepository.save(meetingRequest);

        return modelMapper.map(updateRequestMeeting, MeetingRequestDTO.class);

    }

    public MeetingRequestDTO mapToDTO(MeetingRequest meetingRequest) {
        return modelMapper.map(meetingRequest, MeetingRequestDTO.class);
    }
    public MeetingRequest mapToEntity(MeetingRequestDTO meetingRequestDTO) {
        return modelMapper.map(meetingRequestDTO, MeetingRequest.class);
    }
}
