package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.util.Date;

@Data
public class SemesterDTO {
    private int id;
    private String semesterName;
    private Date dateStart;
    private Date dateEnd;
    private int year;
    private Long adminId;
}
