package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.WeeklyEmptySlot;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
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
    ModelMapper mapper;
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
        weeklyDTO.setFirstDateOfWeek(sqlStartDate);
        weeklyDTO.setLastDateOfWeek(sqlEndDate);

        WeeklyEmptySlot responseWeekly = mapper.map(weeklyDTO, WeeklyEmptySlot.class);
        // save to DB
//        weeklySlotRepository.save(responseWeekly);

        return weeklyDTO;
    }

}
