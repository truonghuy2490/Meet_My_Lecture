package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.service.WeeklyEmptySlotService;
import org.springframework.stereotype.Service;

@Service
public class WeeklyEmptySlotServiceImpl implements WeeklyEmptySlotService {
    private WeeklyEmptySlotService weeklyEmptySlotService;

    public WeeklyEmptySlotServiceImpl(WeeklyEmptySlotService weeklyEmptySlotService) {
        this.weeklyEmptySlotService = weeklyEmptySlotService;
    }
}