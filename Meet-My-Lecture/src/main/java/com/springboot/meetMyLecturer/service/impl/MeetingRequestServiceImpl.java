package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.GlobalExceptionHandler;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.*;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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


    //student create request DONE
    @Override
    public MeetingRequestDTO createRequest(Long studentId, Long lecturerId,String subjectId, String requestContent) {
        User student = userRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId))
        );
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );

        MeetingRequest meetingRequest = new MeetingRequest();

        Date currentDate = new Date();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());

        meetingRequest.setSubject(subject);
        meetingRequest.setStudent(student);
        meetingRequest.setLecturer(lecturer);
        meetingRequest.setRequestStatus("Pending");
        meetingRequest.setCreateAt(date);
        meetingRequest.setRequestContent(requestContent);

        meetingRequestRepository.save(meetingRequest);


        return modelMapper.map(meetingRequest,MeetingRequestDTO.class);
    }

    //student delete request DONE
    @Override
    public String deleteRequest(Long requestId, Long studentId) {
        MeetingRequest meetingRequest = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );

        if(!meetingRequest.getStudent().getUserId().equals(studentId)){
            throw new RuntimeException("You do not have this request.");
        }

        meetingRequestRepository.deleteById(requestId);

        return "This meeting request has been deleted!";
    }


    // TRUOC KHI ASSIGN
    @Override
    public MeetingRequestDTO processRequest(MeetingRequest meetingRequest, Long requestId) {
        MeetingRequestDTO meetingRequestDTO = modelMapper.map(meetingRequest, MeetingRequestDTO.class);
        MeetingRequest meetingRequestDB = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );
        // SET STATUS
        meetingRequestDB.setRequestStatus(meetingRequestDTO.getRequestStatus());

        MeetingRequest responseRequest = meetingRequestRepository.save(modelMapper.map(meetingRequestDB, MeetingRequest.class));

        return modelMapper.map(responseRequest, MeetingRequestDTO.class);
    }

    @Override
    public List<MeetingRequestDTO> getRequestByUserId(Long lecturerId) {
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(lecturerId))
        );
        List<MeetingRequest> requestList = meetingRequestRepository.findMeetingRequestByLecturerUserId(lecturerId);
        if(requestList.isEmpty()){
            throw new RuntimeException("There are no request");
        }

        return requestList.stream().map(
                meetingRequest -> modelMapper.map(
                        meetingRequest, MeetingRequestDTO.class
                )
        ).collect(Collectors.toList());
    }
    // SAU KHI ASSIGN - UPDATE EMPTY = updateStudentIdInSlot

    //lecturer get all requests
    @Override
    public List<MeetingRequestDTO> getAllRequestByUserId(Long userId) {

        List<MeetingRequest> meetingRequestList = meetingRequestRepository.findMeetingRequestByStudent_UserId(userId);

        List<MeetingRequestDTO> meetingRequestDTOList = meetingRequestList.stream().map(
                meetingRequest -> {
                    MeetingRequestDTO dto = new MeetingRequestDTO();
                    dto.setRequestStatus(meetingRequest.getRequestStatus());
                    dto.setRequestContent(meetingRequest.getRequestContent());


                    return dto;
                }).collect(Collectors.toList());

        return meetingRequestDTOList;
    }

    @Override
    public List<MeetingRequestDTO> getAllRequests() {
        List<MeetingRequest> meetingRequestList = meetingRequestRepository.findAll();
        if(meetingRequestList.isEmpty()){
            throw new RuntimeException("There are no meeting requests!");
        }
        List<MeetingRequestDTO> meetingRequestDTOList = meetingRequestList.stream().map(
                meetingRequest -> {
                    MeetingRequestDTO meetingRequestDTO = modelMapper.map(meetingRequest, MeetingRequestDTO.class);
                    return meetingRequestDTO;
                }
        ).collect(Collectors.toList());
        return meetingRequestDTOList;
    }

    // student update request DONE
    @Override
    public MeetingRequestDTO updateRequest(String requestContent, String subjectId, Long requestId)
    {
        MeetingRequest meetingRequestDB = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );

        meetingRequestDB.setSubject(subject);
        meetingRequestDB.setRequestContent(requestContent);
        meetingRequestRepository.save(meetingRequestDB);

        MeetingRequestDTO meetingRequestDTO = modelMapper.map(meetingRequestDB,MeetingRequestDTO.class);
        meetingRequestDTO.setStudentName(meetingRequestDB.getStudent().getUserName());
        meetingRequestDTO.setLecturerName(meetingRequestDB.getLecturer().getUserName());
        meetingRequestDTO.setSubjectId(meetingRequestDB.getSubject().getSubjectId());

        return meetingRequestDTO;
    }
}

