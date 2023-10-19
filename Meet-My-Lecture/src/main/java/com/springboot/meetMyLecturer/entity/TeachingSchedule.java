package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teaching_schedule")
public class TeachingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_schedule_id")
    private int teachingScheduleId;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    // moi add ne
    @Column(name = "date_of_week")
    private String dateOfWeek;

    private int roomId;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

}
