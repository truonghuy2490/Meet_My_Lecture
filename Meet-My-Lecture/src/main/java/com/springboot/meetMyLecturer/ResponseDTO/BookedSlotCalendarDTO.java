package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class BookedSlotCalendarDTO {

    private Long emptySlotId;

    private String roomId;

    private Timestamp bookedDate;

    private String subjectId;

    private String status;

    private String description;

    private Time timeStart;

    private Time duration;

    private Date dateStart;

    private String lecturerName;

}
