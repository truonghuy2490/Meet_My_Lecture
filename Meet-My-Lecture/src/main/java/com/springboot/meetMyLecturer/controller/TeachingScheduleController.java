package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;
import com.springboot.meetMyLecturer.service.ImportTeachingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("api/v1/schedule")
public class TeachingScheduleController {
    @Autowired
    ImportTeachingScheduleService importTeachingScheduleService;

    @PostMapping("lecturer/{lecturerId}")
    public ResponseEntity<Set<TeachingScheduleDTO>> createTeachingScheduleByLecId(
            @PathVariable Long lecturerId,
            @RequestBody Set<TeachingScheduleDTO> teachingScheduleDTO
    )
    {
        Set<TeachingScheduleDTO> responseSchedule = importTeachingScheduleService.createTeachingSchedule(
                teachingScheduleDTO,
                lecturerId
        );
        return new ResponseEntity<>(responseSchedule, HttpStatus.CREATED);
    }
    @DeleteMapping("{scheduleId}/lecturer/{lecturerId}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long lecturerId,
            @PathVariable Long scheduleId
    ){
        importTeachingScheduleService.deleteSchedule(lecturerId, scheduleId);
        return new ResponseEntity<>("Delete successfully!", HttpStatus.OK);
    }

    @PutMapping("lecturer/{lecturerId}")
    public ResponseEntity<String> setStatusForTeachingSchedule(@PathVariable Long lecturerId,
                                               @RequestParam String status){
        String result = importTeachingScheduleService.setStatusForTeachingSchedule(lecturerId, status);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
