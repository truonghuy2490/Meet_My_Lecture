package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;
import java.sql.Time;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empty_slot")
public class EmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotId;


    private int studentId;

    @Column(name = "lecturer_id", nullable = false)
    private int lecturerId;

    private String subjectId;

    private int weeklySlotId;

    private String roomId;

    private String status;

    private String description;

    @Column(name = "time_start", nullable = false)
    private Time timeStart;

    @Column(name = "duration", nullable = false)
    private Time duration;

    private Date dateStart;

    private Date bookedDate;

    private int code;




}
