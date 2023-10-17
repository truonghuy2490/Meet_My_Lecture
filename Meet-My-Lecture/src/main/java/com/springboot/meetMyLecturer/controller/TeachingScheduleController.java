package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.TeachingSchedule;
import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;
import com.springboot.meetMyLecturer.service.ImportTeachingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teaching-schedule")
public class TeachingScheduleController {
    @Autowired
    ImportTeachingScheduleService importTeachingScheduleService;

    @GetMapping("lecturer/{lecturerId}")
    public List<TeachingScheduleDTO> getTeachingScheduleByLectureId(
            @PathVariable Long lecturerId)
    {
        return importTeachingScheduleService.getTeachingScheduleByLectureId(lecturerId);
    }

    @PostMapping("lecturer/{lecturerId}/subject/{subjectId}")
    public ResponseEntity<TeachingScheduleDTO> createTeachingScheduleByLecId(
            @PathVariable Long lecturerId,
            @PathVariable String subjectId,
            @RequestBody TeachingSchedule teachingSchedule
    )
    {
        TeachingScheduleDTO teachingScheduleDTO = importTeachingScheduleService.createTeachingSchedule(
                teachingSchedule,
                lecturerId,
                subjectId
        );
        return new ResponseEntity<>(teachingScheduleDTO, HttpStatus.CREATED);
    }
}
