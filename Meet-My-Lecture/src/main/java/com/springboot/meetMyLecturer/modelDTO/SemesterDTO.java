package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.sql.Date;

@Data
public class SemesterDTO {
    private String semesterName;

    private Date dateStart;

    private Date dateEnd;

    private String status;

    private int year;
}
