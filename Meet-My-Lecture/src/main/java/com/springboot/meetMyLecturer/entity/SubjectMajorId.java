package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SubjectMajorId implements Serializable {
    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "major_id")
    private Long majorId;
}
