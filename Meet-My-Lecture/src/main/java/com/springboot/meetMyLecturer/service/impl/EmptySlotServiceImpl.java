package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
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


    //lecturer create empty slot DONE
    @Override
    public EmptySlotResponseDTO creatEmptySlot(Long lecturerId, EmptySlotDTO emptySlotDTO) {

        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        String roomId = emptySlotDTO.getRoomId();
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", roomId)
        );

        int SlotTimeId = emptySlotDTO.getSlotTimeId();
        SlotTime slotTime = slotTimeRepository.findById(SlotTimeId).orElseThrow(
                () -> new ResourceNotFoundException("Slot time", "id", String.valueOf(SlotTimeId))
        );


        // get Weekly [if not have in db, create new week]
        WeeklyDTO weeklyDTO = weeklyEmptySlotService.createWeeklyByDateAt(emptySlotDTO.getDateStart());
        WeeklyEmptySlot weeklyEmptySlot = new WeeklyEmptySlot();
        Date dateStart = new Date(weeklyDTO.getFirstDateOfWeek().getTime());
        Date dateEnd = new Date(weeklyDTO.getLastDateOfWeek().getTime());
        weeklyEmptySlot.setFirstDayOfWeek(dateStart);
        weeklyEmptySlot.setLastDayOfWeek(dateEnd);
        weeklySlotRepository.save(weeklyEmptySlot);

        EmptySlot emptySlot = new EmptySlot();
        emptySlot.setLecturer(lecturer);
        emptySlot.setSlotTime(slotTime);
        emptySlot.setRoom(room);
        emptySlot.setWeeklySlot(weeklyEmptySlot);

        java.sql.Date date = new java.sql.Date(emptySlotDTO.getDateStart().getTime());

        emptySlot.setDateStart(date);
        emptySlot.setDuration(Time.valueOf(emptySlotDTO.getDuration().toLocalTime()));
        emptySlot.setTimeStart(Time.valueOf(emptySlotDTO.getTimeStart().toLocalTime()));

        emptySlot.setStatus("Open");

        emptySlotRepository.save(emptySlot);

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

    // check if slot is expired
    @Scheduled(cron = "0 0 6,12 * * ?")
    public void checkIfEmptySlotIsExpired() {

        java.sql.Date dateNow = java.sql.Date.valueOf(LocalDate.now());

        Time timeNow = Time.valueOf(LocalTime.now());
        Time plus = Time.valueOf("06:00:00");
        LocalTime timePlus = plus.toLocalTime();
        Time timeLater = Time.valueOf(addTimes(timeNow.toLocalTime(), timePlus)) ;

        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsByStatus(Constant.OPEN);

        for (EmptySlot emptySlot : emptySlotList) {
            java.sql.Date dateStart = emptySlot.getDateStart();
            Time timeStart = emptySlot.getTimeStart();

            if (dateStart.before(dateNow)) {
                emptySlot.setStatus("EXPIRED");
                emptySlotRepository.save(emptySlot);

            } else if (dateStart.equals(dateNow)) {
                if (timeStart.after(timeNow) && timeStart.before(timeLater)) {
                    emptySlot.setStatus("EXPIRED");
                    emptySlotRepository.save(emptySlot);
                }
            }
        }


    }

    public static LocalTime addTimes(LocalTime time1, LocalTime time2) {
        Duration duration1 = Duration.between(LocalTime.MIDNIGHT, time1);
        Duration duration2 = Duration.between(LocalTime.MIDNIGHT, time2);

        Duration totalDuration = duration1.plus(duration2);

        return LocalTime.MIDNIGHT.plus(totalDuration);
    }

}
