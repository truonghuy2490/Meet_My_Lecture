package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class SubjectSemesterId implements Serializable {

    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "semester_id")
    private Long semesterId;
}
