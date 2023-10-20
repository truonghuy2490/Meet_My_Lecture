package com.springboot.meetMyLecturer.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.meetMyLecturer.modelDTO.WeeklyDTO;
import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/weekly")
public class WeeklyController {
    @Autowired
    WeeklyEmptySlotService weeklyEmptySlotService;

    @GetMapping
    public List<WeeklyDTO> getAllWeekly(){
        List<WeeklyDTO> list = weeklyEmptySlotService.getAllWeekly();
        return list;
    }
    @PostMapping()
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    public ResponseEntity<WeeklyDTO> createWeekly(
            @RequestBody Date date
    ){
        WeeklyDTO responseWeekly = weeklyEmptySlotService.createWeeklyByDateAt(date);
        return new ResponseEntity<>(responseWeekly, HttpStatus.CREATED);
    }

}
