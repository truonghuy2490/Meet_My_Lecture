package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.*;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRequestServiceImpl implements MeetingRequestService {
    @Autowired
    MeetingRequestRepository meetingRequestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;


    @Autowired
    ModelMapper modelMapper;


    //student create request
    @Override
    public MeetingRequestDTO createRequest(Long studentId, Long lecturerId,String subjectId, MeetingRequest meetingRequest) {
        User student = userRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId))
        );
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        java.sql.Date date = java.sql.Date.valueOf(localDate);

        meetingRequest.setSubject(subject);
        meetingRequest.setStudent(student);
        meetingRequest.setLecturer(lecturer);
        meetingRequest.setRequestStatus("Pending");
        meetingRequest.setCreateAt(date);

        meetingRequestRepository.save(meetingRequest);

        MeetingRequestDTO meetingRequestDTO = modelMapper.map(meetingRequest,MeetingRequestDTO.class);

        UserDTO lecturerDTO = modelMapper.map(lecturer,UserDTO.class);
        UserDTO studentDTO = modelMapper.map(student,UserDTO.class);
        SubjectResponseDTO subjectDTO = modelMapper.map(subject, SubjectResponseDTO.class);

        meetingRequestDTO.setLecturer(lecturerDTO);
        meetingRequestDTO.setStudent(studentDTO);
        meetingRequestDTO.setSubject(subjectDTO);
        meetingRequestDTO.setRequestStatus(meetingRequest.getRequestStatus());

        return meetingRequestDTO;
    }

    //student delete request
    @Override
    public String deleteRequest(Long requestId) {
        meetingRequestRepository.deleteById(requestId);
        return "This meeting request has been deleted!";
    }

    //lecturer get all requests
    @Override
    public List<MeetingRequestDTO> getAllRequest() {

        List<MeetingRequest> meetingRequestList = meetingRequestRepository.findAll();

        List<MeetingRequestDTO> meetingRequestDTOList = meetingRequestList.stream().map(
                meetingRequest -> {
                    MeetingRequestDTO dto = new MeetingRequestDTO();
                    dto.setRequestStatus(meetingRequest.getRequestStatus());
                    dto.setRequestContent(meetingRequest.getRequestContent());

                    UserDTO studentDTO = modelMapper.map(meetingRequest.getStudent(),UserDTO.class);
                    UserDTO lecturerDTO = modelMapper.map(meetingRequest.getLecturer(),UserDTO.class);
                    SubjectResponseDTO subjectDTO = modelMapper.map(meetingRequest.getSubject(), SubjectResponseDTO.class);

                    dto.setStudent(studentDTO);
                    dto.setLecturer(lecturerDTO);
                    dto.setSubject(subjectDTO);

                    return dto;
                }).collect(Collectors.toList());

        return meetingRequestDTOList;
    }

    @Override
    public MeetingRequestDTO updateRequest(MeetingRequest meetingRequest, String subjectId, Long requestId)
    {
        MeetingRequest meetingRequestDB = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );

        meetingRequestDB.setSubject(subject);
        meetingRequestDB.setRequestContent(meetingRequest.getRequestContent());
        meetingRequestRepository.save(meetingRequestDB);

        MeetingRequestDTO meetingRequestDTO = modelMapper.map(meetingRequestDB,MeetingRequestDTO.class);

        SubjectResponseDTO subjectResponseDTO = modelMapper.map(subject, SubjectResponseDTO.class);
        meetingRequestDTO.setSubject(subjectResponseDTO);

        return meetingRequestDTO;
    }
}

