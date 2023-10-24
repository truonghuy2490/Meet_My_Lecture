package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.sql.Date;

@Data
public class WeeklyEmptySlotResponseForAdminDTO {
    private Long weeklySlotId;

    private Long semesterId;

    private Date firstDayOfWeek;

    private Date lastDayOfWeek;

    private String status;
}
