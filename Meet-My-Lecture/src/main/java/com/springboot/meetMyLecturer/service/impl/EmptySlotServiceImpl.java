package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.EmptySlotRepository;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmptySlotServiceImpl implements EmptySlotService {
    @Autowired
    ModelMapper mapper;

    @Autowired
    EmptySlotRepository emptySlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRequestRepository meetingRequestRepository;

//    @Override
//    public List<EmptySlotDTO> getAllEmptySlot() {
//        List<EmptySlot> emptySlots = emptySlotRepository.findAll();
//        return emptySlots.stream().map(emptySlot -> mapper.map(emptySlot, EmptySlotDTO.class)).collect(Collectors.toList());
//    }

    @Override
    public List<EmptySlotDTO> getAllEmptySlotByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        List<EmptySlot> slots = emptySlotRepository.findEmptySlotByLecturer_UserId(userId);
        if(slots.isEmpty()){
            throw new RuntimeException("There no empty slot by this user");
        }
        return slots.stream().map(
                slot -> mapper.map(slot, EmptySlotDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public EmptySlotDTO creatEmptySlot(Long lecturerId, EmptySlot emptySlot) {

        EmptySlotDTO emptySlotDTO = mapper.map(emptySlot, EmptySlotDTO.class);
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(lecturerId))
        );
        emptySlot.setLecturer(user);

        UserDTO userDTO = mapper.map(user, UserDTO.class);

        emptySlotDTO.setLecturer(userDTO);
        emptySlotDTO.setStatus("Open");

        EmptySlot newEmptySlot = emptySlotRepository.save(mapper.map(emptySlotDTO, EmptySlot.class));

        return mapper.map(newEmptySlot, EmptySlotDTO.class);

    }

    @Override
    public EmptySlotDTO assignRequestToSlot(Long meetingRequestId, Long slotId) {
       EmptySlot emptySlot = emptySlotRepository.findById(slotId).orElseThrow(
               () -> new ResourceNotFoundException("Empty", "id", String.valueOf(slotId))
       );
       EmptySlotDTO emptySlotDTO = mapper.map(emptySlot, EmptySlotDTO.class);
       MeetingRequest meetingRequest = meetingRequestRepository.findById(meetingRequestId).orElseThrow(
               () -> new ResourceNotFoundException("Meeting Request", "id", String.valueOf(meetingRequestId))
       );
        MeetingRequestDTO meetingRequestDTO = mapper.map(meetingRequest, MeetingRequestDTO.class);

       emptySlotDTO.setStudent(meetingRequestDTO.getStudent());
       emptySlotDTO.setRequest(meetingRequestDTO);
       emptySlotDTO.setSubject(meetingRequestDTO.getSubject());

       emptySlotRepository.save(mapper.map(emptySlotDTO, EmptySlot.class));
       return emptySlotDTO;
    }

    // SAU KHI ASSIGN - UPDATE EMPTY = updateStudentIdInSlot
}
