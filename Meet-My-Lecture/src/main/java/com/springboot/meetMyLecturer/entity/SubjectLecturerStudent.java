package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subject_lecturer_student")
public class SubjectLecturerStudent {

    @Id
    @ManyToOne
    @JoinColumn(name = "lecturer_id", referencedColumnName = "user_id")
    private User lecturer;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private User student;

    @Id
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;
}
