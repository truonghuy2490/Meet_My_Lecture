package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class BookedSlotHomePageDTO {
    private int slotId;

    private String status;

    private String description;

    private Time timeStart;

    private Time duration;

    private Date dateStart;

    private String lecturerName;

    private int slotTimeId;

}
