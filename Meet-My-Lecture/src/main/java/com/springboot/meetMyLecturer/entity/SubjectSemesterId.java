package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
@Data
@Embeddable
public class SubjectSemesterId implements Serializable {

    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "semester_id")
    private Long semesterId;
}
