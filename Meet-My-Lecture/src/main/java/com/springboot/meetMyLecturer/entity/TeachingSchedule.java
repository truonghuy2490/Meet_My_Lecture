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
    @Column(name = "teaching_schedule_id")
    private int teachingScheduleId;

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    private int roomId;

    private Date date;

    private Time time;

}
