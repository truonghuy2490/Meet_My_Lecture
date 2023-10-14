package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
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

    @Autowired
    MapToDTO mapToDTO;


    @Override
    public MeetingRequestDTO createRequest(int studentId,int lecturerId,MeetingRequest meetingRequest) {
            MeetingRequestDTO meetingRequestDTO = mapToDTO.mapMeetingRequestToDto(meetingRequest);

            User student = userRepository.findUserByUserId(studentId);
            User lecturer = userRepository.findUserByUserId(lecturerId);

            meetingRequest.setStudent(student);
            meetingRequest.setLecturer(lecturer);
        List<MeetingRequest> meetingRequests = meetingRequestRepository.findAll();
        return meetingRequests.stream().map(meetingRequest -> mapToDTO(meetingRequest)).collect(Collectors.toList());
    }

            UserDTO lecturerDTO = mapToDTO.mapUserToDto(lecturer);
            UserDTO studentDTO = mapToDTO.mapUserToDto(student);
    @Override
    public MeetingRequestDTO updateRequest(MeetingRequestDTO meetingRequestDTO, Long id) {
        MeetingRequest meetingRequest = meetingRequestRepository.findById(id).orElseThrow(() -> new ResourceNoFoundException("Request Meeting", "id", id));

        meetingRequest.setRequestId(meetingRequestDTO.getRequestId());
        meetingRequest.setRequestStatus(meetingRequestDTO.getRequestStatus());
        meetingRequest.setRequestContent(meetingRequestDTO.getRequestContent());

        MeetingRequest updateRequestMeeting = meetingRequestRepository.save(meetingRequest);

        return mapToDTO(updateRequestMeeting);

            meetingRequestDTO.setStudent(studentDTO);
            meetingRequestDTO.setLecturer(lecturerDTO);

            meetingRequestRepository.save(meetingRequest);

            return meetingRequestDTO;
        }


    public MeetingRequestDTO mapToDTO(MeetingRequest meetingRequest) {
        return modelMapper.map(meetingRequest, MeetingRequestDTO.class);
    }
    public MeetingRequest mapToEntity(MeetingRequestDTO meetingRequestDTO) {
        return modelMapper.map(meetingRequestDTO, MeetingRequest.class);
    }
}
