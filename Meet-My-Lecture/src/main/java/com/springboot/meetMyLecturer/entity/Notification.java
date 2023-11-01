package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;
    @Column(name = "notification_message")
    private String notificationMessage;
    @Column(name = "timestamp")
    private LocalDate timestamp;
    @Column(name = "notification_type")
    private String notificationType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private EmptySlot emptySlot;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private MeetingRequest meetingRequest;


}
