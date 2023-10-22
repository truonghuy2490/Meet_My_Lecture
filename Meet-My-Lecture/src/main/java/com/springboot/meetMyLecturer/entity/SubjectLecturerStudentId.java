package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SubjectLecturerStudentId implements Serializable {

    @Column(name = "lecturer_id")
    private Long lecturerId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "subject_id")
    private String subjectId;

}
