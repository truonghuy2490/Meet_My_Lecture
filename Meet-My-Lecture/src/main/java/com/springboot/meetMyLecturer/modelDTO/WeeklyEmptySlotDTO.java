package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyEmptySlotDTO {

    private Long weeklyEmptySlotId;

    private Long semesterId;

    private LocalDate firstDayOfWeek;

    private LocalDate endDayOfWeek;

}
