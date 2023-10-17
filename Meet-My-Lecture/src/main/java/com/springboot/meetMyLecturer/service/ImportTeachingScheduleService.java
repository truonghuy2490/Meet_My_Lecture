package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.TeachingSchedule;
import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;

import java.util.List;

public interface ImportTeachingScheduleService {
    List<TeachingScheduleDTO> getTeachingScheduleByLectureId(Long lecturerId);
    TeachingScheduleDTO createTeachingSchedule(TeachingSchedule teachingSchedule, Long lecturerId);
    void deleteSchedule(Long lecturerId, Long scheduleId);
}
