package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "meeting_request")
public class MeetingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    @Column(name = "student_id", nullable = false)
    private int studentId;

    @Column(name = "lecturer_id", nullable = false)
    private int lecturerId;

    private int slotId;

    private String requestContent;

    private String requestStatus;

}
