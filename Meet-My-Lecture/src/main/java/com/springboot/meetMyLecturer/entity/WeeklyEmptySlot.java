package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "Weekly_Empty_Slots", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class WeeklyEmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int weeklySlotId;

    private Date firstDateOfWeek;
    private Date lastDateOfWeek;
    private Year year;


}
