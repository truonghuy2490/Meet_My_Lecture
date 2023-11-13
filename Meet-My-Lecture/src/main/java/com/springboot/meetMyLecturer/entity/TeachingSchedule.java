package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teaching_schedule")
public class TeachingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_schedule_id")
    private Long teachingScheduleId;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


    private Date date;

    private String roomId;

    private String status;

    @ManyToOne
    @JoinColumn(name = "slot_time_id", nullable = false)
    private SlotTime slot;

}
