package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseDTO;
import com.springboot.meetMyLecturer.entity.Semester;
import com.springboot.meetMyLecturer.entity.WeeklyEmptySlot;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyEmptySlotDTO;
import com.springboot.meetMyLecturer.repository.SemesterRepository;
import com.springboot.meetMyLecturer.repository.WeeklySlotRepository;
import com.springboot.meetMyLecturer.service.SemesterService;
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
    ModelMapper mapper;
    @Autowired
    SemesterService semesterService;
    @Autowired
    SemesterRepository semesterRepository;
    @Override
    public List<WeeklyDTO> getAllWeekly() {
        List<WeeklyEmptySlot> weeklyEmptySlots = weeklySlotRepository.findAll();
        List<WeeklyDTO> weeklyDTOList = weeklyEmptySlots.stream().map(
                weeklyEmptySlot -> mapper.map(weeklyEmptySlot,WeeklyDTO.class)
        ).collect(Collectors.toList());
        return weeklyDTOList;
    }

    @Override
    public WeeklyDTO createWeekly(WeeklyDTO weeklyDTO) {
        WeeklyEmptySlot weeklyEmptySlot = mapper.map(weeklyDTO, WeeklyEmptySlot.class);
        weeklySlotRepository.save(weeklyEmptySlot);

        return weeklyDTO;
    }

    @Override
    public WeeklyDTO createWeeklyByDateAt(Date date) {
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

        // Create a WeeklyDTO object and set the start and end dates as java.sql.Date
        WeeklyDTO weeklyDTO = new WeeklyDTO();
        weeklyDTO.setFirstDayOfWeek(sqlStartDate);
        weeklyDTO.setLastDayOfWeek(sqlEndDate);

        WeeklyEmptySlot responseWeekly = mapper.map(weeklyDTO, WeeklyEmptySlot.class);
        // save to DB
//        weeklySlotRepository.save(responseWeekly);

        return weeklyDTO;
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

        if( checkWeeklyDate == null) {
            // Check if exist 1 weekly has day start
            if (!startDate.equals(checkWeeklyDate)) {
                // if it does not exist, create a WeeklyDTO object and set the start and end dates as java.sql.Date
                // if create weekly NEED to add into Semester !!
                WeeklyEmptySlot weeklyEmptySlot = new WeeklyEmptySlot();
                weeklyEmptySlot.setFirstDayOfWeek(sqlStartDate);
                weeklyEmptySlot.setLastDayOfWeek(sqlEndDate);

                // insert into semester - If there are avaiable semester
                Semester semester = semesterRepository.findSemesterByDateStart(sqlStartDate);
                if (semester == null){
                    throw new RuntimeException("This semester are not avaiable to booking");
                }
                weeklyEmptySlot.setSemester(semester);

                // save to DB
                weeklySlotRepository.save(weeklyEmptySlot);

                return mapper.map(weeklyEmptySlot, WeeklyDTO.class);
            }
        }
        // Get Weekly Available
        WeeklyEmptySlot weeklyEmptySlot = weeklySlotRepository.findWeeklyEmptySlotByFirstDayOfWeek(sqlStartDate);
        return mapper.map(weeklyEmptySlot, WeeklyDTO.class);
    }

    // view all week for amin
    @Override
    public List<WeeklyEmptySlotResponseDTO> viewAllWeeks() {
        List<WeeklyEmptySlot> weeklyEmptySlotList = weeklySlotRepository.findAll();

        if(weeklyEmptySlotList.isEmpty()){
            throw new RuntimeException("Error");
        }

        return weeklyEmptySlotList.stream().map(weeklyEmptySlot -> mapper.map(weeklyEmptySlot, WeeklyEmptySlotResponseDTO.class)).collect(Collectors.toList());
    }

    //edit week for admin
    @Override
    public WeeklyEmptySlotResponseDTO editWeeklyEmptySlot(Long weeklyEmptySlotId, WeeklyEmptySlotDTO weeklyEmptySlotDTO) {
        WeeklyEmptySlot weeklyEmptySlot = weeklySlotRepository.findById(weeklyEmptySlotId).orElseThrow(
                ()-> new ResourceNotFoundException("This week","id",String.valueOf(weeklyEmptySlotId))
        );

        return mapper.map(weeklyEmptySlot, WeeklyEmptySlotResponseDTO.class);
    }

}
