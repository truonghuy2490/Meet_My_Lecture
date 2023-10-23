package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyEmptySlotDTO;

import java.util.Date;
import java.util.List;

public interface WeeklyEmptySlotService {
    WeeklyDTO createWeeklyByDateAt(Date date);
    List<WeeklyEmptySlotResponseDTO> viewAllWeeks();
    String deleteWeeklyEmptySlot(Long weeklyEmptySlotId);

    List<EmptySlotResponseDTO> getEmptySlotsInWeek(WeeklyEmptySlotDTO weeklyEmptySlotDTO);

}
