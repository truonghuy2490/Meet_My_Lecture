package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject_major")
@IdClass(SubjectMajorId.class)
public class SubjectMajor {

    @Id
    @Column(name = "subject_id")
    private String subjectId;

    @Id
    @Column(name = "major_id")
    private String majorId;
}
