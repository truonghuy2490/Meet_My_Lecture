package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weekly-empty-slots")
public class WeeklyEmptySlotController {
    private WeeklyEmptySlotService weeklyEmptySlotService;

    public WeeklyEmptySlotController(WeeklyEmptySlotService weeklyEmptySlotService) {
        this.weeklyEmptySlotService = weeklyEmptySlotService;
    }
    //CRUD
}
