package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Getter
@Setter
@Table(name = "Empty_Slots", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class EmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long slotId;

    private Time timeStart;
    private Time duration;
    private Date date;
    private String status;
    private String roomId;
    private String description;

    public EmptySlot(Time timeStart, Time duration, Date date, String status, String roomId, String description) {
        this.timeStart = timeStart;
        this.duration = duration;
        this.date = date;
        this.status = status;
        this.roomId = roomId;
        this.description = description;
    }

    public EmptySlot() {

    }
}
