package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.WeeklyEmptySlotResponseForAdminDTO;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;

import java.util.Date;
import java.util.List;

public interface WeeklyEmptySlotService {
    WeeklyDTO createWeeklyByDateAt(Date date);
    List<WeeklyEmptySlotResponseForAdminDTO> viewAllWeeks();
    String updateWeeklyEmptySlotStatus(Long weeklyEmptySlotId, String status);
    List<EmptySlotResponseDTO> getEmptySlotsInWeek(Long lecturerId, Long weeklyEmptySlotId);

}
