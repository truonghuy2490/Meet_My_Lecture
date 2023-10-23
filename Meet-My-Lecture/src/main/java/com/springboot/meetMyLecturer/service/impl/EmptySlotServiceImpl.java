package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import com.springboot.meetMyLecturer.utils.SlotUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;




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
    @Autowired
    SlotUtils slotUtils;


    //lecturer create empty slot DONE
    @Override
    public EmptySlotResponseDTO creatEmptySlot(Long lecturerId, EmptySlotDTO emptySlotDTO) {

        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        String roomId = emptySlotDTO.getRoomId();
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", String.valueOf(roomId))
        );

        int SlotTimeId = emptySlotDTO.getSlotTimeId();
        SlotTime slotTime = slotTimeRepository.findById(SlotTimeId).orElseThrow(
                () -> new ResourceNotFoundException("Slot time", "id", String.valueOf(SlotTimeId))
        );


        // CHECK IF THERE ARE ANY BOOKED AT THIS SLOT TIME
        if(!isSlotAvaiable(emptySlotDTO)){
            throw new RuntimeException("There are Slot booked before!");
        }

        // [DONE] - get Weekly [if not have in db, create new week]
        WeeklyDTO weeklyDTO = weeklyEmptySlotService.insertIntoWeeklyByDateAt(emptySlotDTO.getDateStart());

        WeeklyEmptySlot weeklyEmptySlot = new WeeklyEmptySlot();
        Date dateStart = new Date(weeklyDTO.getFirstDateOfWeek().getTime());
        Date dateEnd = new Date(weeklyDTO.getLastDateOfWeek().getTime());

        weeklyEmptySlot.setFirstDayOfWeek(dateStart);
        weeklyEmptySlot.setLastDayOfWeek(dateEnd);

        EmptySlot emptySlot = mapper.map(emptySlotDTO, EmptySlot.class);
        emptySlot.setLecturer(lecturer);
        emptySlot.setSlotTime(slotTime);
        emptySlot.setRoom(room);
        emptySlot.setWeeklySlot(weeklyEmptySlot);

        emptySlot.setDateStart(emptySlotDTO.getDateStart());
        emptySlot.setDuration(Time.valueOf(emptySlotDTO.getDuration().toLocalTime()));
        emptySlot.setTimeStart(Time.valueOf(emptySlotDTO.getTimeStart().toLocalTime()));

        emptySlot.setStatus("Open");

        // save to DB
//        emptySlotRepository.save(emptySlot);

        return mapper.map(emptySlot, EmptySlotResponseDTO.class);
    }


    //assign meeting request to empty slot DONE
    @Override
    public EmptySlotResponseDTO assignRequestToSlot(Long meetingRequestId, Long emptySlotId) {
       EmptySlot emptySlot = emptySlotRepository.findById(emptySlotId).orElseThrow(
               () -> new ResourceNotFoundException("Empty", "id", String.valueOf(emptySlotId))
       );

       MeetingRequest meetingRequest = meetingRequestRepository.findById(meetingRequestId).orElseThrow(
               () -> new ResourceNotFoundException("Meeting Request", "id", String.valueOf(meetingRequestId))
       );

        emptySlot.setSubject(meetingRequest.getSubject());
        emptySlot.setStudent(meetingRequest.getStudent());
        emptySlot.setBookedDate(meetingRequest.getCreateAt());
        emptySlotRepository.save(emptySlot);

        return mapper.map(emptySlot, EmptySlotResponseDTO.class);
    }

    public boolean isSlotAvaiable(EmptySlotDTO emptySlotDTO){
        boolean check = true;
        // check date
        List<EmptySlot> emptySlots = emptySlotRepository.findEmptySlotByDateStart(emptySlotDTO.getDateStart());

        for(int i = 0; i < emptySlots.size(); i++){

            // check status
            if(emptySlots.get(i).getStatus().equals("Open")){

                // check Slot
                if(emptySlots.get(i).getSlotTime().getSlotTimeId() == emptySlotDTO.getSlotTimeId()){

                    LocalTime startTime = emptySlots.get(i).getTimeStart().toLocalTime();
                    LocalTime duration = emptySlots.get(i).getDuration().toLocalTime();
                    LocalTime endTimeExist = addTimes(startTime, duration);
                    LocalTime newStartTime = emptySlotDTO.getTimeStart().toLocalTime();

                    // Check if newStartTime is within the existing time slot
                    if (newStartTime.compareTo(startTime) >= 0 && newStartTime.compareTo(endTimeExist) <= 0) {
                        throw new RuntimeException("Slot have been booked already !"); // Replace this with your desired action or error message.
                    }
                }
            }
        }

        return check;

    }

    public static LocalTime addTimes(LocalTime time1, LocalTime time2) {
        Duration duration1 = Duration.between(LocalTime.MIDNIGHT, time1);
        Duration duration2 = Duration.between(LocalTime.MIDNIGHT, time2);

        Duration totalDuration = duration1.plus(duration2);

        return LocalTime.MIDNIGHT.plus(totalDuration);
    }

}
