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
import com.springboot.meetMyLecturer.utils.ConvertLecturerName;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
        // Create a ModelMapper instance
        ModelMapper modelMapper = new ModelMapper();

        // Define the mapping for other properties
        TypeMap<EmptySlot, BookedSlotHomePageDTO> typeMap = modelMapper.createTypeMap(EmptySlot.class, BookedSlotHomePageDTO.class);

        // Explicitly map LecturerName
        typeMap.addMapping(src -> src.getLecturer().getUserName() != null ? src.getLecturer().getUserName() : src.getLecturer().getNickName(), BookedSlotHomePageDTO::setLecturerName);

        // Map the entities to DTOs
        return slots.stream()
                .map(slot -> modelMapper.map(slot, BookedSlotHomePageDTO.class))
                .collect(Collectors.toList());
    }


    //lecturer create empty slot DONE
    @Override
    public BookedSlotHomePageDTO creatEmptySlot(Long lecturerId, EmptySlotDTO emptySlotDTO) {


        // retrieve Lecturer
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
//        emptySlot.setLecturer(lecturer);

        // retrieve room
        String roomId = emptySlotDTO.getRoomId();
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", String.valueOf(roomId))
        );
//        emptySlot.setRoom(room);

        // retrieve to Slot Time
        int SlotTimeId = emptySlotDTO.getSlotTimeId();
        SlotTime slotTime = slotTimeRepository.findById(SlotTimeId).orElseThrow(
                () -> new ResourceNotFoundException("Slot time", "id", String.valueOf(SlotTimeId))
        );
//        emptySlot.setSlotTime(slotTime);

        // get Weekly [if not have in db, create new week]
        WeeklyDTO weeklyDTO = weeklyEmptySlotService.createWeeklyByDateAt(emptySlotDTO.getDateStart());
        WeeklyEmptySlot weeklyEmptySlot = mapper.map(weeklyDTO, WeeklyEmptySlot.class);
        weeklySlotRepository.save(weeklyEmptySlot);

        EmptySlot emptySlot = new EmptySlot();
        emptySlot.setLecturer(lecturer);
        emptySlot.setSlotTime(slotTime);
        emptySlot.setRoom(room);
        emptySlot.setWeeklySlot(weeklyEmptySlot);

        emptySlot.setDateStart(emptySlotDTO.getDateStart());
        emptySlot.setDuration(emptySlotDTO.getDuration());
        emptySlot.setTimeStart(emptySlotDTO.getTimeStart());

        emptySlot.setStatus("Open");

        emptySlotRepository.save(emptySlot); // save db

        BookedSlotHomePageDTO bookedSlotHomePageDTO = new BookedSlotHomePageDTO();
        bookedSlotHomePageDTO.setSlotTimeId(slotTime.getSlotTimeId());
        bookedSlotHomePageDTO.setLecturerName(lecturer.getUserName());
        bookedSlotHomePageDTO.setDateStart(emptySlot.getDateStart());
        bookedSlotHomePageDTO.setSlotId(emptySlot.getId());
        bookedSlotHomePageDTO.setDuration(emptySlot.getDuration());
        bookedSlotHomePageDTO.setTimeStart(emptySlot.getTimeStart());
        bookedSlotHomePageDTO.setStatus(emptySlot.getStatus());
        bookedSlotHomePageDTO.setDescription(emptySlot.getDescription());


        return bookedSlotHomePageDTO;
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
