package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.Date;

@Data
@Entity
@Table(
        name = "Weekly_Empty_Slots",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = (""))
        }
)
public class WeeklyEmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int weeklySlotId;

    @Column(name = "first_date_of_week", nullable = false)
    private Date firstDateOfWeek;
    @Column(name = "last_date_of_week", nullable = false)
    private Date lastDateOfWeek;
    @Column(name = "year", nullable = false)
    private Year year;


}
