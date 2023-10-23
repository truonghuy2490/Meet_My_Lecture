package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.Semester;
import lombok.Data;

import java.time.DayOfWeek;
import java.sql.Date;

@Data
public class WeeklyDTO {
    private int id;
    private String semesterName;
    private Date firstDateOfWeek;
    private Date lastDateOfWeek;
}
