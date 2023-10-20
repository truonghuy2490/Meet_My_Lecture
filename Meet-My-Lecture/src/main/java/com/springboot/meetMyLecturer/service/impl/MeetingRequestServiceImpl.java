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
            throw new RuntimeException("This meeting request is accepted. Please update information ");
        }

        meetingRequestRepository.deleteById(requestId);

        return "This meeting request has been deleted!";
    }


    // TRUOC KHI ASSIGN
    @Override
    public MeetingRequestResponseDTO processRequest(MeetingRequest meetingRequest, Long requestId) {
        MeetingRequestResponseDTO meetingRequestResponseDTO = modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
        MeetingRequest meetingRequestDB = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );
        // SET STATUS
        meetingRequestDB.setRequestStatus(meetingRequestResponseDTO.getRequestStatus());

        MeetingRequest responseRequest = meetingRequestRepository.save(modelMapper.map(meetingRequestDB, MeetingRequest.class));

        return modelMapper.map(responseRequest, MeetingRequestResponseDTO.class);
    }

    @Override
    public List<MeetingRequestResponseDTO> getRequestByUserId(Long lecturerId) {
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

    //lecturer get all requests
    @Override
    public List<MeetingRequestResponseDTO> getAllRequestByUserId(Long userId) {

        List<MeetingRequest> meetingRequestList = meetingRequestRepository.findMeetingRequestByStudent_UserId(userId);

        List<MeetingRequestResponseDTO> meetingRequestResponseDTOList = meetingRequestList.stream().map(
                meetingRequest -> {
                    MeetingRequestResponseDTO dto = new MeetingRequestResponseDTO();
                    dto.setRequestStatus(meetingRequest.getRequestStatus());
                    dto.setRequestContent(meetingRequest.getRequestContent());


                    return dto;
                }).collect(Collectors.toList());

        return meetingRequestResponseDTOList;
    }

    @Override
    public List<MeetingRequestResponseDTO> getAllRequests() {
        List<MeetingRequest> meetingRequestList = meetingRequestRepository.findAll();
        if(meetingRequestList.isEmpty()){
            throw new RuntimeException("There are no meeting requests!");
        }
        List<MeetingRequestResponseDTO> meetingRequestResponseDTOList = meetingRequestList.stream().map(
                meetingRequest -> {
                    MeetingRequestResponseDTO meetingRequestResponseDTO = modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
                    return meetingRequestResponseDTO;
                }
        ).collect(Collectors.toList());
        return meetingRequestResponseDTOList;
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

        meetingRequest.setSubject(subject);
        meetingRequest.setRequestContent(requestContent);
        meetingRequestRepository.save(meetingRequest);

        MeetingRequestResponseDTO meetingRequestResponseDTO = modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
        meetingRequestResponseDTO.setStudentName(meetingRequest.getStudent().getUserName());
        meetingRequestResponseDTO.setLecturerName(meetingRequest.getLecturer().getUserName());
        meetingRequestResponseDTO.setSubjectId(meetingRequest.getSubject().getSubjectId());

        return meetingRequestResponseDTO;
    }


}

