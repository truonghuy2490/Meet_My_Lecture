package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RequestResponse;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public MeetingRequestResponseDTO createRequest(Long studentId, Long lecturerId, String subjectId, MeetingRequestDTO meetingRequestDTO) {
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

        meetingRequest.setSubject(subject);
        meetingRequest.setStudent(student);
        meetingRequest.setLecturer(lecturer);
        meetingRequest.setRequestStatus("Pending");
        meetingRequest.setCreateAt(meetingRequestDTO.getCreateAt());
        meetingRequest.setRequestContent(meetingRequestDTO.getRequestContent());

        meetingRequestRepository.save(meetingRequest);

        return modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
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

        if(meetingRequest.getRequestStatus().equals("Accepted")){
            throw new RuntimeException("This meeting request is accepted. Please update information in booked slot.");
        }

        meetingRequestRepository.deleteById(requestId);

        return "This meeting request has been deleted!";
    }


    // process request for lecturer DONE
    @Override
    public MeetingRequestResponseDTO processRequest(String status, Long requestId) {

        MeetingRequest meetingRequest = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );

        meetingRequest.setRequestStatus(status);

        meetingRequestRepository.save(meetingRequest);

        return modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
    }

    //get all requests by lecturerId for lecturer DONE
    @Override
    public List<MeetingRequestResponseDTO> getRequestByLecturerId(Long lecturerId) {
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(lecturerId))
        );
        List<MeetingRequest> requestList = meetingRequestRepository.findMeetingRequestByLecturerUserId(lecturerId);
        if(requestList.isEmpty()){
            throw new RuntimeException("There are no request");
        }

        return requestList.stream().map(
                meetingRequest -> modelMapper.map(
                        meetingRequest, MeetingRequestResponseDTO.class
                )
        ).collect(Collectors.toList());
    }
    // SAU KHI ASSIGN - UPDATE EMPTY = updateStudentIdInSlot

    //student get all requests DONE
    @Override
    public List<MeetingRequestResponseDTO> getAllRequestByStudentId(Long studentId) {

        List<MeetingRequest> meetingRequestList = meetingRequestRepository.findMeetingRequestByStudent_UserId(studentId);

        return meetingRequestList.stream().map(
                meetingRequest -> modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class))
                .collect(Collectors.toList());
    }

    // student update request DONE
    @Override
    public MeetingRequestResponseDTO updateRequest(String requestContent, String subjectId, Long requestId)
    {
        MeetingRequest meetingRequest = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );

        if(!meetingRequest.getRequestStatus().equals("Accepted")){
            throw new RuntimeException("This meeting request is accepted. Please update information in booked slot.");
        }

        meetingRequest.setSubject(subject);
        meetingRequest.setRequestContent(requestContent);
        meetingRequestRepository.save(meetingRequest);

        return modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
    }


}

