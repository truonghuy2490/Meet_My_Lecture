package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empty_slot")
public class EmptySlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slot_id")
    private int id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "lecturer_id", nullable = false)
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = true)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "weekly_slot_id")
    private WeeklyEmptySlot weeklySlot;

    @ManyToOne
    @JoinColumn(name = "slot_time_id")
    private SlotTime slotTime;

    @OneToMany(mappedBy = "emptySlot")
    private Set<Notification> notifications;

    @OneToOne
    @JoinColumn(name = "meeting_request_id")
    private MeetingRequest meetingRequest;

    private String roomId;

    @Column(name = "status", nullable = false)
    private String status;

    private String description;

    @Column(name = "time_start", nullable = false)
    private Time timeStart;

    @Column(name = "duration", nullable = false)
    private Time duration;

    private Date dateStart;

    private Timestamp bookedDate;

    private Integer code;

}
