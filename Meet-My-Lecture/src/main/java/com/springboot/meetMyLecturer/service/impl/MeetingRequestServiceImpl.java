package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.*;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    SubjectRepository subjectRepository;


    @Autowired
    ModelMapper modelMapper;


    //student create request
    @Override
    public MeetingRequestDTO createRequest(int studentId, int lecturerId,String subjectId, MeetingRequest meetingRequest) {
        MeetingRequestDTO meetingRequestDTO = modelMapper.map(meetingRequest,MeetingRequestDTO.class);

        User student = userRepository.findUserByUserId(studentId);
        User lecturer = userRepository.findUserByUserId(lecturerId);
        Subject subject = subjectRepository.findSubjectBySubjectId(subjectId);


        meetingRequest.setSubject(subject);
        meetingRequest.setStudent(student);
        meetingRequest.setLecturer(lecturer);

        meetingRequestRepository.save(meetingRequest);

        UserDTO lecturerDTO = modelMapper.map(lecturer,UserDTO.class);
        UserDTO studentDTO = modelMapper.map(student,UserDTO.class);
        SubjectResponseRequestDTO subjectDTO = modelMapper.map(subject, SubjectResponseRequestDTO.class);

        meetingRequestDTO.setLecturer(lecturerDTO);
        meetingRequestDTO.setStudent(studentDTO);
        meetingRequestDTO.setSubject(subjectDTO);


        return meetingRequestDTO;
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
                    SubjectResponseRequestDTO subjectDTO = modelMapper.map(meetingRequest.getSubject(), SubjectResponseRequestDTO.class);

                    dto.setStudent(studentDTO);
                    dto.setLecturer(lecturerDTO);
                    dto.setSubject(subjectDTO);

                    return dto;
                }).collect(Collectors.toList());

        return meetingRequestDTOList;
    }

    @Override
    public MeetingRequestDTO updateRequest(MeetingRequestDTO meetingRequestDTO, Long id) {
        return null;
    }
}

    /*@Override
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

}*/
