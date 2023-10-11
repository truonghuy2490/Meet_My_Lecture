package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teaching_schedule")
public class TeachingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teachingScheduleId;

    private int lecturerId;

    private String subjectId;

    private int roomId;

    private Date date;

    private Time time;

}
