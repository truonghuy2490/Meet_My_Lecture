package com.springboot.meetMyLecturer.modelDTO;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Time;
import java.sql.Date;

@Data
public class EmptySlotDTO {
    private int emptySlotid;

    private Date dateStart;

    private Time timeStart;

    private Time duration;

    private String roomId;
    
    private Integer slotTimeId;

    private String mode;

    private String status;

}
