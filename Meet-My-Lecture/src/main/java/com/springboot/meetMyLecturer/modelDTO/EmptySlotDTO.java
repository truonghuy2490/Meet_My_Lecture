package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.sql.Time;
import java.sql.Date;

@Data
public class EmptySlotDTO {

    private int slotTimeId;

    private Date dateStart;

    private Time timeStart;

    private Time duration;

    private String roomId;

    private String mode;

}
