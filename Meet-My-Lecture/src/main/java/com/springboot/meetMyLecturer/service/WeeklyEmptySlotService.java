package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseForAdminDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;


import java.util.Date;
import java.util.List;

public interface WeeklyEmptySlotService {
    List<WeeklyDTO> getAllWeekly();

    WeeklyDTO createWeekly(WeeklyDTO weeklyDTO);

    WeeklyDTO createWeeklyByDateAt(Date date);

    List<WeeklyEmptySlotResponseForAdminDTO> viewAllWeeks();

    List<EmptySlotResponseDTO> getEmptySlotsInWeek(Long lecturerId, Long weeklyEmptySlotId);

    WeeklyDTO insertIntoWeeklyByDateAt(Date date);

    String updateWeeklyEmptySlotStatus(Long weeklyEmptySlotId, String status);

    WeeklyEmptySlotResponseDTO editWeeklyEmptySlot(Long weeklyEmptySlotId, WeeklyDTO weeklyEmptySlotDTO);
}
