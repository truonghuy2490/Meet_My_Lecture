package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class EmptySlotDTO {
    private int slotId;

    private String roomId;

    private String status;

    private String description;

    private Time timeStart;

    private Time duration;

    private Date dateStart;

    private Date bookedDate;

    private UserDTO student;

    private UserDTO lecturer;
    private SubjectResponseDTO subject;
}
