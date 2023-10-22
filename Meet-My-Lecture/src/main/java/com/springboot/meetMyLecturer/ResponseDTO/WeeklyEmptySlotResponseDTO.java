package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyEmptySlotResponseDTO {
    private Long weekly_slot_id;

    private Long semester_id;

    private LocalDate firstDayOfWeek;

    private LocalDate endDayOfWeek;
}
