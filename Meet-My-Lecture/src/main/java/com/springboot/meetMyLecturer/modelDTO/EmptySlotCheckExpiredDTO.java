package com.springboot.meetMyLecturer.modelDTO;

import jakarta.persistence.Entity;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class EmptySlotCheckExpiredDTO {
    private Long emptySlotId;
    private Date dateStart;
    private Time timeStart;
}
