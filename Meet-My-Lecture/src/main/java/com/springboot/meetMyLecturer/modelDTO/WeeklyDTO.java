package com.springboot.meetMyLecturer.modelDTO;


import lombok.Data;
import java.sql.Date;

@Data
public class WeeklyDTO {
    private int weeklySlotId;
    private String semesterName;
    private Date firstDayOfWeek;
    private Date lastDayOfWeek;
}
