package com.springboot.meetMyLecturer.modelDTO;


import lombok.Data;
import java.sql.Date;

@Data
public class WeeklyDTO {
    private Date firstDateOfWeek;
    private Date lastDateOfWeek;
}
