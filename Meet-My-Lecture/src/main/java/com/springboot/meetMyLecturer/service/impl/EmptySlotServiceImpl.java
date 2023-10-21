package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;

import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.repository.*;
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
    @Autowired
    WeeklySlotRepository weeklySlotRepository;

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
    public BookedSlotCalendarDTO creatEmptySlot(Long lecturerId, BookedSlotCalendarDTO bookedSlotCalendarDTO) {

        // revert to slot entity
        EmptySlot emptySlot = mapper.map(bookedSlotCalendarDTO, EmptySlot.class);

        // retrieve Lecturer
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        emptySlot.setLecturer(lecturer);

        // retrieve room
        Room room = roomRepository.findById(bookedSlotCalendarDTO.getRoomId()).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", String.valueOf(bookedSlotCalendarDTO.getRoomId()))
        );
        emptySlot.setRoom(room);

        // retrieve to Slot Time
        int SlotId = emptySlot.getSlotTime().getSlotTimeId();
        SlotTime slotTime = slotTimeRepository.findById(SlotId).orElseThrow(
                () -> new ResourceNotFoundException("Slot time", "id", String.valueOf(SlotId))
        );
        emptySlot.setSlotTime(slotTime);

        // add Slot to Weekly
        WeeklyDTO weeklyDTO = weeklyEmptySlotService.createWeeklyByDateAt(emptySlot.getDateStart());
        emptySlot.setWeeklySlot(mapper.map(weeklyDTO, WeeklyEmptySlot.class));

        // revert to dto
        BookedSlotCalendarDTO newSlotDTO = mapper.map(emptySlot, BookedSlotCalendarDTO.class);
        newSlotDTO.setLecturerName(emptySlot.getLecturer().getUserName());
        newSlotDTO.setRoomId(emptySlot.getRoom().getRoomId());

        newSlotDTO.setStatus("OPEN");

        // save to db
        emptySlotRepository.save(mapper.map(newSlotDTO, EmptySlot.class));

        return newSlotDTO;
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

    //--

}
