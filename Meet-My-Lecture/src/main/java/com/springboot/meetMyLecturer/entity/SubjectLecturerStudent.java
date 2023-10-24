package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject_lecturer_student")
public class SubjectLecturerStudent {

    @EmbeddedId
    private SubjectLecturerStudentId subjectLecturerStudentId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @MapsId("studentId")
    private User student;


    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    @MapsId("lecturerId")
    private User lecturer;


    @ManyToOne
    @JoinColumn(name = "subject_id")
    @MapsId("subjectId")
    private Subject subject;

    @JoinColumn(name = "status")
    private String status;

}
