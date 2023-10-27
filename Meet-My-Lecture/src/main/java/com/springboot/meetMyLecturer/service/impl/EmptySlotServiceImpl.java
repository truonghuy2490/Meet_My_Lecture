package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.EmptySlotService;
import com.springboot.meetMyLecturer.service.NotificationService;
import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import com.springboot.meetMyLecturer.utils.NotificationType;
import com.springboot.meetMyLecturer.utils.SlotUtils;
import org.modelmapper.ModelMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
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
    @Autowired
    SlotUtils slotUtils;
    @Autowired
    NotificationService notificationService;


    @Override
    public SlotResponse getAllSlot(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);


        // SAVE TO REPO
        Page<EmptySlot> slots = emptySlotRepository.findAll(pageable);

        // get content for page object
        List<EmptySlot> listOfSlots = slots.getContent();

        List<EmptySlotResponseDTO> content = listOfSlots.stream().map(
                slot -> mapper.map(slot, EmptySlotResponseDTO.class)
        ).collect(Collectors.toList());

        SlotResponse slotResponse = new SlotResponse();
        slotResponse.setContent(content);
        slotResponse.setTotalPage(slots.getTotalPages());
        slotResponse.setTotalElement(slots.getTotalElements());
        slotResponse.setPageNo(slots.getNumber());
        slotResponse.setPageSize(slots.getSize());
        slotResponse.setLast(slots.isLast());

        return slotResponse;
    }

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
        WeeklyEmptySlot weeklyEmptySlot = mapper.map(weeklyDTO,WeeklyEmptySlot.class);

        EmptySlot emptySlot = mapper.map(emptySlotDTO, EmptySlot.class);

        // set entity
        emptySlot.setLecturer(lecturer);
        emptySlot.setSlotTime(slotTime);
        emptySlot.setRoom(room);
        emptySlot.setWeeklySlot(weeklyEmptySlot);

        // set attribute
        emptySlot.setDateStart(emptySlotDTO.getDateStart());
        emptySlot.setDuration(Time.valueOf(emptySlotDTO.getDuration().toLocalTime()));
        emptySlot.setTimeStart(Time.valueOf(emptySlotDTO.getTimeStart().toLocalTime()));

        if(emptySlotDTO.getMode().equalsIgnoreCase(Constant.PRIVATE)){
            emptySlot.setCode(generateRandomNumber());
        } // check private slot and create code

        emptySlot.setStatus("Open");

        // save to DB
        emptySlotRepository.save(emptySlot);

        // Create and save a notification
        String notificationMessage = "Slot created in room " + emptySlot.getRoom().getRoomId() +
                " at " + emptySlot.getDateStart() + " " + emptySlot.getTimeStart().toLocalTime() +
                " for slot duration " + emptySlot.getDuration();
        NotificationType notificationType = NotificationType.SlotCreate;
        //notificationService.slotNotification(notificationMessage, notificationType, emptySlot);


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

       if(!meetingRequest.getRequestStatus().equals("Approved")){
           throw new RuntimeException("Please wait for teacher approve!");
       }

        emptySlot.setSubject(meetingRequest.getSubject());
        emptySlot.setStudent(meetingRequest.getStudent());
        emptySlot.setBookedDate(meetingRequest.getCreateAt());
        emptySlotRepository.save(emptySlot);

        return mapper.map(emptySlot, EmptySlotResponseDTO.class);
    }

    @Override
    public EmptySlotResponseDTO updateEmptySlot(Long lecturerId, Long emptySlotId, EmptySlotDTO emptySlotDTO) {

        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        EmptySlot emptySlot = emptySlotRepository.findById(emptySlotId).orElseThrow(
                () -> new ResourceNotFoundException("Slot", "id", String.valueOf(emptySlotId))
        );
        // if does not exist slot id
        if(!emptySlot.getLecturer().getUserId().equals(lecturer.getUserId())){
            throw new RuntimeException("Slot not belong to this lecturer");
        }

        // if duplicate with other slot
        if(!isSlotAvaiable(emptySlotDTO)){
            throw new RuntimeException("There are Slot booked before!");
        }

        // updating
        emptySlot.setDateStart(emptySlotDTO.getDateStart()); // update date start -> update weekly
        emptySlot.setDuration(Time.valueOf(emptySlotDTO.getDuration().toLocalTime()));
        emptySlot.setTimeStart(Time.valueOf(emptySlotDTO.getTimeStart().toLocalTime()));


        // [DONE] - get Weekly [if not have in db, create new week]
        WeeklyDTO weeklyDTO = weeklyEmptySlotService.insertIntoWeeklyByDateAt(emptySlotDTO.getDateStart());

        WeeklyEmptySlot weeklyEmptySlot = new WeeklyEmptySlot();
        Date dateStart = new Date(weeklyDTO.getFirstDayOfWeek().getTime());
        Date dateEnd = new Date(weeklyDTO.getLastDayOfWeek().getTime());

        weeklyEmptySlot.setFirstDayOfWeek(dateStart);
        weeklyEmptySlot.setLastDayOfWeek(dateEnd);

        emptySlot.setWeeklySlot(weeklyEmptySlot);

        // save to DB
        emptySlotRepository.save(emptySlot);

        // Update and save a notification
        String notificationMessage = "Slot update in room " + emptySlot.getRoom().getRoomId() +
                " at " + emptySlot.getDateStart() + " " + emptySlot.getTimeStart().toLocalTime() +
                " for slot duration " + emptySlot.getDuration();
        NotificationType notificationType = NotificationType.SlotUpdate;
       // notificationService.slotNotification(notificationMessage, notificationType, emptySlot);

        return mapper.map(emptySlot, EmptySlotResponseDTO.class);

    }

    @Override
    public EmptySlotResponseDTO deleteSlot(Long lecturerId, Long emptySlotId, EmptySlotDTO emptySlotDTO) {

        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "id", String.valueOf(lecturerId))
        );
        EmptySlot emptySlot = emptySlotRepository.findById(emptySlotId).orElseThrow(
                () -> new ResourceNotFoundException("Slot", "id", String.valueOf(emptySlotId))
        );
        // if does not exist slot id
        if(!emptySlot.getLecturer().getUserId().equals(lecturer.getUserId())){
            throw new RuntimeException("Slot not belong to this lecturer");
        }

        // if duplicate with other slot
        if(!isSlotAvaiable(emptySlotDTO)){
            throw new RuntimeException("There are Slot booked before!");
        }

        // CLOSE SLOT
        emptySlot.setStatus(Constant.CLOSED);


        // save to DB
        emptySlotRepository.save(emptySlot);

        // delete and save a notification
        String notificationMessage = "Slot delete success !";
        NotificationType notificationType = NotificationType.SlotDelete;
        notificationService.slotNotification(notificationMessage, notificationType, emptySlot);

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

                    } else if (emptySlots.get(i).getRoom().getRoomId().equals(emptySlotDTO.getRoomId())) {
                        throw  new RuntimeException("This have been booked!");
                    }
                }
            }
        }

        return check;

    }


    // check if slot is expired
    @Scheduled(cron = "0 0 22,6 * * ?")
    public void checkIfEmptySlotIsExpired() {

        java.sql.Date dateNow = java.sql.Date.valueOf(LocalDate.now());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        java.sql.Date nextDate = new java.sql.Date(calendar.getTime().getTime());

        Time timeNow = Time.valueOf(LocalTime.now());

        List<Long> emptySlotList = emptySlotRepository.findEmptySlotsByStatus(Constant.OPEN);

        for (Long emptySlotId : emptySlotList) {
            EmptySlot emptySlotDB = emptySlotRepository.findById(emptySlotId).orElseThrow();
            java.sql.Date dateStart = emptySlotDB.getDateStart();
            Time timeStart = emptySlotDB.getTimeStart();

            if (dateStart.before(dateNow)) {
                emptySlotDB.setStatus("EXPIRED");
                emptySlotRepository.save(emptySlotDB);

                //expired slots from 12:00AM-20:00PM at 6AM at the same day
            } else if (dateStart.equals(dateNow)) {
                if (timeStart.after(Time.valueOf("12:00:00")) && timeStart.before(Time.valueOf("20:00:00"))) {
                    emptySlotDB.setStatus("EXPIRED");
                    emptySlotRepository.save(emptySlotDB);
                }
                // expired slots from 6:00AM-12:00AM at 10PM the day before
            }else if(dateStart.after(nextDate) && timeNow.equals(Time.valueOf("22:00:00"))){
                if(timeStart.after(Time.valueOf("06:00:00")) && timeStart.before(Time.valueOf("12:00:00"))){
                    emptySlotDB.setStatus("EXPIRED");
                    emptySlotRepository.save(emptySlotDB);
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

    public int generateRandomNumber() {
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        return random.nextInt(max - min + 1) + min;
    }

}
