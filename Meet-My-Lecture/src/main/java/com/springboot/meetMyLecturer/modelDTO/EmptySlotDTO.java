package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.*;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

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

    private int code;

    private UserDTO users;
}
