package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyEmptySlotDTO;

import java.util.Date;
import java.util.List;

public interface WeeklyEmptySlotService {
    List<WeeklyDTO> getAllWeekly();
    WeeklyDTO createWeekly(WeeklyDTO weeklyDTO);
    WeeklyDTO createWeeklyByDateAt(Date date);
    WeeklyDTO insertIntoWeeklyByDateAt(Date date);
    List<WeeklyEmptySlotResponseDTO> viewAllWeeks();

    WeeklyEmptySlotResponseDTO editWeeklyEmptySlot(Long weeklyEmptySlotId, WeeklyEmptySlotDTO weeklyEmptySlotDTO);
}
