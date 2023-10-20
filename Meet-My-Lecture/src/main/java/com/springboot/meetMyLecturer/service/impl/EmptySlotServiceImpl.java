package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.EmptySlotRepository;
import com.springboot.meetMyLecturer.repository.MeetingRequestRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmptySlotServiceImpl implements EmptySlotService {
    @Autowired
    ModelMapper mapper;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    EmptySlotRepository emptySlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRequestRepository meetingRequestRepository;
    @Autowired
    WeeklyEmptySlotService weeklyEmptySlotService;

    @Autowired
    SlotTimeRepository slotTimeRepository;

    @Override
    public List<BookedSlotHomePageDTO> getAllEmptySlotByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        List<EmptySlot> slots = emptySlotRepository.findEmptySlotByLecturer_UserId(userId);
        if(slots.isEmpty()){
            throw new RuntimeException("There no empty slot by this user");
        }
        return slots.stream().map(
                slot -> mapper.map(slot, BookedSlotHomePageDTO.class)
        ).collect(Collectors.toList());
    }

    //lecturer create empty slot DONE
    @Override
    public BookedSlotCalendarDTO creatEmptySlot(Long lecturerId, EmptySlotDTO emptySlotDTO) {
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        Room  room = roomRepository.findById(emptySlotDTO.getRoomId()).orElseThrow(
                ()-> new ResourceNotFoundException("Room","id", emptySlotDTO.getRoomId())
        );
        SlotTime slotTime = slotTimeRepository.findById(emptySlotDTO.getSlotTimeId()).orElseThrow(
                ()-> new ResourceNotFoundException("Slot time","id",String.valueOf(emptySlotDTO.getSlotTimeId()))
        );

        bookedSlotHomePageDTO.setLecturerName(userDTO.getUserName());
        // Auto add slot to Weekly
        weeklyEmptySlotService.createWeeklyByDateAt(bookedSlotHomePageDTO.getDateStart());

        bookedSlotHomePageDTO.setStatus("Open");
        EmptySlot emptySlot = new EmptySlot();

        // save to db
        EmptySlot newEmptySlot = emptySlotRepository.save(mapper.map(bookedSlotHomePageDTO, EmptySlot.class));
        emptySlot.setSlotTime(slotTime);
        emptySlot.setRoom(room);
        emptySlot.setDateStart(emptySlotDTO.getDateStart());
        emptySlot.setDuration(emptySlotDTO.getDuration());
        emptySlot.setTimeStart(emptySlotDTO.getTimeStart());
        emptySlot.setLecturer(lecturer);
        emptySlot.setStatus("Open");

        emptySlotRepository.save(emptySlot);

        return mapper.map(emptySlot, BookedSlotCalendarDTO.class);
    }

    //assign meeting request to empty slot DONE
    @Override
    public BookedSlotCalendarDTO assignRequestToSlot(Long meetingRequestId, Long emptySlotId) {
       EmptySlot emptySlot = emptySlotRepository.findById(emptySlotId).orElseThrow(
               () -> new ResourceNotFoundException("Empty", "id", String.valueOf(emptySlotId))
       );
        BookedSlotCalendarDTO bookedSlotCalendarDTO = mapper.map(emptySlot, BookedSlotCalendarDTO.class);
       MeetingRequest meetingRequest = meetingRequestRepository.findById(meetingRequestId).orElseThrow(
               () -> new ResourceNotFoundException("Meeting Request", "id", String.valueOf(meetingRequestId))
       );
        MeetingRequestResponseDTO meetingRequestResponseDTO = mapper.map(meetingRequest, MeetingRequestResponseDTO.class);


       emptySlotRepository.save(mapper.map(bookedSlotCalendarDTO, EmptySlot.class));

       return bookedSlotCalendarDTO;
    }

    // SAU KHI ASSIGN - UPDATE EMPTY = updateStudentIdInSlot
}
