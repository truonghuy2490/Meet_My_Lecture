package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestForStudentDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RequestResponse;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.MeetingRequestService;
import com.springboot.meetMyLecturer.service.NotificationService;
import com.springboot.meetMyLecturer.utils.NotificationType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

    @Autowired
    NotificationService notificationService;

    //student create request DONE-DONE
    @Override
    public MeetingRequestResponseDTO createRequest(Long studentId, MeetingRequestForStudentDTO meetingRequestDTO) {
        User student = userRepository.findUserByUserIdAndStatus(studentId, Constant.OPEN);
        if(student == null) throw new RuntimeException("This student is not existed.");

        User lecturer = userRepository.findUserByUserIdAndStatus(meetingRequestDTO.getLecturerId(), Constant.OPEN);
        if(lecturer == null) throw new RuntimeException("This lecturer is not existed.");

        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(meetingRequestDTO.getSubjectId(), Constant.OPEN);
        if(subject == null) throw new RuntimeException("This subject is not existed.");

        MeetingRequest meetingRequest = new MeetingRequest();

        meetingRequest.setSubject(subject);
        meetingRequest.setStudent(student);
        meetingRequest.setLecturer(lecturer);
        meetingRequest.setRequestStatus(Constant.PENDING);
        meetingRequest.setCreateAt(LocalDateTime.now());
        meetingRequest.setRequestContent(meetingRequestDTO.getRequestContent());

        meetingRequestRepository.save(meetingRequest);

        // Create and save a notification
        String notificationMessage = "Requesting " + meetingRequest.getSubject().getSubjectId()  +
                " to " + lecturer.getUserName() + " was created";
        NotificationType notificationType = NotificationType.SlotCreate;
        notificationService.requestNotification(notificationMessage, notificationType, meetingRequest);

        return modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
    }

    //student delete request DONE - DONE
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
    public MeetingRequestResponseDTO processRequest(MeetingRequestDTO meetingRequestDTO, Long requestId, Long lecturerId) {

        MeetingRequest meetingRequest = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        if(!meetingRequest.getLecturer().getUserId().equals(lecturer.getUserId())){
            throw new RuntimeException("This request not belong to this lecturer");
        }

        meetingRequest.setRequestStatus(meetingRequestDTO.getRequestStatus());

        meetingRequestRepository.save(meetingRequest);

        return modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
    }

    //get all requests by lecturerId for lecturer DONE-DONE
    @Override
    public List<MeetingRequestResponseDTO> getRequestByLecturerId(Long lecturerId) {
        User user = userRepository.findUserByUserIdAndStatus(lecturerId, Constant.OPEN);
        if(user == null) throw new RuntimeException("This lecturer is not existed.");

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

    @Override
    public RequestResponse getAllRequest(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // SAVE TO REPO
        Page<MeetingRequest> requests = meetingRequestRepository.findAll(pageable);

        // get content for page object
        List<MeetingRequest> listOfRequests = requests.getContent();

        List<MeetingRequestResponseDTO> content = listOfRequests.stream().map(request -> modelMapper.map(request, MeetingRequestResponseDTO.class)).collect(Collectors.toList());

        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setContent(content);
        requestResponse.setTotalPage(requests.getTotalPages());
        requestResponse.setTotalElement(requests.getTotalElements());
        requestResponse.setPageNo(requests.getNumber());
        requestResponse.setPageSize(requests.getSize());
        requestResponse.setLast(requests.isLast());

        return requestResponse;
    }

    //student get all requests DONE - DONE
    @Override
    public List<MeetingRequestResponseDTO> getAllRequestByStudentId(Long studentId) {

        User user = userRepository.findUserByUserIdAndStatus(studentId, Constant.OPEN);
        if(user == null) throw new RuntimeException("This student is not existed.");

        List<MeetingRequest> meetingRequestList = meetingRequestRepository.findMeetingRequestByStudent_UserId(studentId);

        if(meetingRequestList.isEmpty()){
            throw new RuntimeException("There are no requests.");
        }

        return meetingRequestList.stream().map(
                meetingRequest -> modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class))
                .collect(Collectors.toList());
    }

    // student update request DONE-DONE
    @Override
    public MeetingRequestResponseDTO updateRequest(String requestContent,Long studentId, String subjectId, Long requestId)
    {
        MeetingRequest meetingRequest = meetingRequestRepository.findById(requestId).orElseThrow(
                () -> new ResourceNotFoundException("Meeting request", "id", String.valueOf(requestId))
        );

        User student = userRepository.findUserByUserIdAndStatus(studentId, Constant.OPEN);
        if(student == null) throw new RuntimeException("This student is not existed.");

        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(subjectId, Constant.OPEN);
        if (subject == null) throw new RuntimeException("This subject is not existed.");

        if(!meetingRequest.getRequestStatus().equals("Accepted")){
            throw new RuntimeException("This meeting request is accepted. Please update information in booked slot.");
        }

        if(!meetingRequest.getStudent().getUserId().equals(studentId)){
            throw new RuntimeException("You do not have this request.");
        }

        meetingRequest.setSubject(subject);
        meetingRequest.setRequestContent(requestContent);
        meetingRequestRepository.save(meetingRequest);

        return modelMapper.map(meetingRequest, MeetingRequestResponseDTO.class);
    }

    // get page request by stu id
    @Override
    public RequestResponse getAllRequestByStudentId(int pageNo, int pageSize, String sortBy, String sortDir, Long studentId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        // SAVE TO REPO
        Page<MeetingRequest> requests = meetingRequestRepository.findAll(pageable); // findAllByStudentId()


        // get content for page object
        List<MeetingRequest> listOfRequests = requests.getContent().stream()
                .filter(request -> request.getStudent().getUserId().equals(studentId))
                .collect(Collectors.toList());

        List<MeetingRequestResponseDTO> content = listOfRequests.stream().map(
                request -> modelMapper.map(request, MeetingRequestResponseDTO.class)
        ).collect(Collectors.toList());

        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setContent(content);
        requestResponse.setTotalPage(requests.getTotalPages());
        requestResponse.setTotalElement(requests.getTotalElements());
        requestResponse.setPageNo(requests.getNumber());
        requestResponse.setPageSize(requests.getSize());
        requestResponse.setLast(requests.isLast());

        return requestResponse;
    }

}

