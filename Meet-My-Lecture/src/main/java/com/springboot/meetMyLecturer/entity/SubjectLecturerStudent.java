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
@IdClass(SubjectLecturerStudentId.class)
public class SubjectLecturerStudent {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @Id
    @Column(name = "lecturer_id")
    private Long lecturerId;

    @Id
    @Column(name = "subject_id")
    private String subjectId;



}
