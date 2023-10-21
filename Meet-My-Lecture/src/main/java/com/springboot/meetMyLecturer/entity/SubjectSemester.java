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
public class SubjectSemester {

    @EmbeddedId
    private SubjectSemesterId subjectSemesterId;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @MapsId("semesterId")
    @JoinColumn(name = "semester_id")
    private Semester semester;

}
