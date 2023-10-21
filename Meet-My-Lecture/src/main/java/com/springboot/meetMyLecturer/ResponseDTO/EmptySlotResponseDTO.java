package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EmptySlotResponseDTO {

    private Long emptySlotId;

    private Long lecturerId;

    private String lecturerName;

    private int slotTimeId;

    private Long studentId;

    private String subjectId;

    private Date dateStart;

    private LocalDateTime bookedDate;

    private String description;

    private  String status;

    private Time timeStart;

    private Time duration;

    private String roomId;
}