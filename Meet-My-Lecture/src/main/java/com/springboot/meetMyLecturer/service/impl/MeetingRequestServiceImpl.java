package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.config.MapToDTO;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
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

            UserDTO lecturerDTO = mapToDTO.mapUserToDto(lecturer);
            UserDTO studentDTO = mapToDTO.mapUserToDto(student);

            meetingRequestDTO.setStudent(studentDTO);
            meetingRequestDTO.setLecturer(lecturerDTO);

            meetingRequestRepository.save(meetingRequest);

            return meetingRequestDTO;
        }
    }
