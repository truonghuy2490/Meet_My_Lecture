package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject_semester")
@IdClass(SubjectSemesterId.class)
public class SubjectSemester {

    @Id
    @Column(name = "subject_id")
    private String subjectId;

    @Id
    @Column(name = "semester_id")
    private Long semesterId;

}
