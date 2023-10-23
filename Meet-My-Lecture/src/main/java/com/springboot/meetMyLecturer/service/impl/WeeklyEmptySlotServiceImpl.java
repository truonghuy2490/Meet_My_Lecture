package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.entity.WeeklyEmptySlot;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyEmptySlotDTO;
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
        weeklyDTO.setFirstDateOfWeek(sqlStartDate);
        weeklyDTO.setLastDateOfWeek(sqlEndDate);

        return weeklyDTO;
    }

    // view all week for amin DONE
    @Override
    public List<WeeklyEmptySlotResponseDTO> viewAllWeeks() {
        List<WeeklyEmptySlot> weeklyEmptySlotList = weeklySlotRepository.findAll();

        if(weeklyEmptySlotList.isEmpty()){
            throw new RuntimeException("Error");
        }

        return weeklyEmptySlotList.stream().map(weeklyEmptySlot -> mapper.map(weeklyEmptySlot, WeeklyEmptySlotResponseDTO.class)).collect(Collectors.toList());
    }


    //delete week for amin DONE
    @Override
    public String deleteWeeklyEmptySlot(Long weeklyEmptySlotId) {
        WeeklyEmptySlot weeklyEmptySlot = weeklySlotRepository.findById(weeklyEmptySlotId).orElseThrow(
                ()-> new ResourceNotFoundException("This week","id",String.valueOf(weeklyEmptySlotId))
        );

        weeklySlotRepository.delete(weeklyEmptySlot);

        return "This week has been deleted!";
    }

    // view empty slot in this week by weekId or weekId and lecturerId
    @Override
    public List<EmptySlotResponseDTO> getEmptySlotsInWeek(WeeklyEmptySlotDTO weeklyEmptySlotDTO) {

        User lecturer = userRepository.findUserByUserId(weeklyEmptySlotDTO.getLecturerId());

        if(lecturer == null){
            List<EmptySlot> emptySlotList = emptySlotRepository.
                    findEmptySlotsByWeeklySlot_WeeklySlotId(weeklyEmptySlotDTO.getWeeklyEmptySlotId());

            return emptySlotList.stream().map(
                    emptySlot -> mapper.map(emptySlot, EmptySlotResponseDTO.class)
            ).collect(Collectors.toList());
        }

        List<EmptySlot> emptySlotList = emptySlotRepository.
                findEmptySlotsByWeeklySlot_WeeklySlotIdAndLecturer_UserId
                        (weeklyEmptySlotDTO.getWeeklyEmptySlotId(), weeklyEmptySlotDTO.getLecturerId());

        return emptySlotList.stream().map(
                emptySlot -> mapper.map(emptySlot, EmptySlotResponseDTO.class)
        ).collect(Collectors.toList());
    }


}
