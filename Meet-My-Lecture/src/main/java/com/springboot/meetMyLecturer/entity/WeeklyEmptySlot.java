package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.Date;

@Data
@Entity
@Table(name = "weekly_empty_slot")
public class WeeklyEmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int weeklySlotId;

    private int semesterId;

    @Column(name = "first_day_of_week", nullable = false)
    private Date firstDateOfWeek;

    @Column(name = "last_day_of_week", nullable = false)
    private Date lastDateOfWeek;

    @Column(name = "year", nullable = false)
    private Year year;


}
