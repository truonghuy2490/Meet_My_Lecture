package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class EmptySlotForStudentDTO {

    private String lecturerName;

    private Date dateStart;

    private  String status;

    private Time timeStart;

    private Time duration;

    private int roomId;
}
