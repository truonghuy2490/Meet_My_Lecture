package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
public class EmptySlotRescheduleDTO {
    private Long lecturerId;

    private int slotTimeId;

    private Long studentId;

    private String studentName;

    private String subjectId;

    private Date dateStart;

    private LocalDateTime bookedDate;

    private String description;

    private  String status;

    private Time timeStart;

    private Time duration;

    private String roomId;

    private String address;
}
