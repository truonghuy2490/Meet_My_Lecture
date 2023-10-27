package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.sql.Date;

@Data
public class SemesterResponseDTO {
    private Long semesterId;
    private String semesterName;
    private Date dateStart;
    private Date dateEnd;
    private int year;
    private String status;
}
