package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class SemesterDTO {
    private String semesterName;

    private Date dateStart;

    private Date dateEnd;

    private int year;

    private Long adminId;
}
