package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseForAdminDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Semester;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.entity.WeeklyEmptySlot;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SlotResponse;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.repository.EmptySlotRepository;
import com.springboot.meetMyLecturer.repository.SemesterRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.repository.WeeklySlotRepository;
import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeeklyEmptySlotServiceImpl implements WeeklyEmptySlotService {
    @Autowired
    WeeklySlotRepository weeklySlotRepository;

    @Autowired
    SemesterRepository semesterRepository;

    @Autowired
    EmptySlotRepository emptySlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public WeeklyDTO createWeeklyByDateAt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);


        Calendar endOfWeekCalendar = (Calendar) calendar.clone();
        endOfWeekCalendar.add(Calendar.DATE, 6);


        Date startDate = calendar.getTime();
        Date endDate = endOfWeekCalendar.getTime();


        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());


        WeeklyDTO weeklyDTO = new WeeklyDTO();
        weeklyDTO.setFirstDayOfWeek(sqlStartDate);
        weeklyDTO.setLastDayOfWeek(sqlEndDate);

        return weeklyDTO;
    }

    // view all week for amin DONE-DONE
    @Override
    public List<WeeklyEmptySlotResponseForAdminDTO> viewAllWeeks() {
        List<WeeklyEmptySlot> weeklyEmptySlotList = weeklySlotRepository.findAll();

        if (weeklyEmptySlotList.isEmpty()) {
            throw new RuntimeException("Error");
        }
        return weeklyEmptySlotList.stream().map(weeklyEmptySlot -> mapper.map(weeklyEmptySlot, WeeklyEmptySlotResponseForAdminDTO.class)).collect(Collectors.toList());
    }

    //delete week for amin DONE-DONE
    @Override
    public String updateWeeklyEmptySlotStatus(Long weeklyEmptySlotId, String status) {
        WeeklyEmptySlot weeklyEmptySlot = weeklySlotRepository.findById(weeklyEmptySlotId).orElseThrow(
                () -> new ResourceNotFoundException("This week", "id", String.valueOf(weeklyEmptySlotId))
        );

        String statusDB = status.toUpperCase();
        if (statusDB.equals(Constant.CLOSED)) {
            weeklyEmptySlot.setStatus(Constant.CLOSED);
        } else if (statusDB.equals(Constant.OPEN)) {
            weeklyEmptySlot.setStatus(Constant.OPEN);
        }

        weeklySlotRepository.save(weeklyEmptySlot);

        return "This week has been " + status.toUpperCase();
    }

    // view empty slot in this week by weekId or weekId and lecturerId DONE-DONE
    @Override
    public List<EmptySlotResponseDTO> getEmptySlotsInWeek(Long lecturerId, Long weeklyEmptySlotId) {

        User lecturer = userRepository.findUserByUserIdAndStatus(lecturerId, Constant.OPEN);
        if (lecturer == null) throw new RuntimeException("This lecturer is not existed");

        if (lecturerId == null) {
            List<EmptySlot> emptySlotList = emptySlotRepository.
                    findEmptySlotsByWeeklySlot_WeeklySlotId(weeklyEmptySlotId);

            return emptySlotList.stream().map(
                    emptySlot -> mapper.map(emptySlot, EmptySlotResponseDTO.class)
            ).collect(Collectors.toList());
        }

        List<EmptySlot> emptySlotList = emptySlotRepository.
                findEmptySlotsByWeeklySlot_WeeklySlotIdAndLecturer_UserId
                        (weeklyEmptySlotId, lecturerId);

        return emptySlotList.stream().map(
                emptySlot -> mapper.map(emptySlot, EmptySlotResponseDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public WeeklyDTO insertIntoWeeklyByDateAt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Define your preferred first day of the week

        // Calculate the end of the week by adding 6 days
        Calendar endOfWeekCalendar = (Calendar) calendar.clone();
        endOfWeekCalendar.add(Calendar.DATE, 6);

        // Extract the start and end dates
        Date startDate = calendar.getTime();
        Date endDate = endOfWeekCalendar.getTime();

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        // find weekly exist first date of week in DB
        WeeklyEmptySlot checkWeeklyDate = weeklySlotRepository.findWeeklyEmptySlotByFirstDayOfWeek(sqlStartDate);

        if (checkWeeklyDate == null) {
            // Check if exist 1 weekly has day start
            if (!startDate.equals(checkWeeklyDate)) {
                // if it does not exist, create a WeeklyDTO object and set the start and end dates as java.sql.Date
                // if create weekly NEED to add into Semester !!
                WeeklyEmptySlot weeklyEmptySlot = new WeeklyEmptySlot();
                weeklyEmptySlot.setFirstDayOfWeek(sqlStartDate);
                weeklyEmptySlot.setLastDayOfWeek(sqlEndDate);

                // insert into semester - If there are avaiable semester
                Semester semester = semesterRepository.findSemesterByDateStart(sqlStartDate);
                if (semester == null) {
                    throw new RuntimeException("This semester are not avaiable to booking");
                }
                weeklyEmptySlot.setSemester(semester);

                // save to DB
                weeklySlotRepository.save(weeklyEmptySlot);

                return mapper.map(weeklyEmptySlot, WeeklyDTO.class);
            }
        }
        WeeklyEmptySlot weeklyEmptySlot = weeklySlotRepository.findWeeklyEmptySlotByFirstDayOfWeek(sqlStartDate);
        return mapper.map(weeklyEmptySlot, WeeklyDTO.class);

    }


}
