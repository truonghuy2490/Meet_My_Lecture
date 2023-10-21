package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class LecturerSubjectId implements Serializable {

    private Long lecturerId;

    private String subjectId;

}
