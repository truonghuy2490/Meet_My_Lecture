package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.TeachingSchedule;
import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;

import java.util.List;
import java.util.Set;

public interface ImportTeachingScheduleService {
    List<TeachingScheduleDTO> getTeachingScheduleByLectureId(Long lecturerId);
    Set<TeachingScheduleDTO> createTeachingSchedule(Set<TeachingScheduleDTO> teachingScheduleDTO, Long lecturerId);
    void deleteSchedule(Long lecturerId, Long scheduleId);

    String setStatusForTeachingSchedule(Long lecturerId, String status);
}
