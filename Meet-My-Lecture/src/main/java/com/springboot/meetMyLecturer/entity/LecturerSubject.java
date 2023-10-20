package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lecturer_subject")
@IdClass(LecturerSubjectId.class)
public class LecturerSubject {
    @Id
    @Column(name = "lecturer_id")
    private Long lecturerId;

    @Id
    @Column(name = "subject_id")
    private String subjectId;

}
