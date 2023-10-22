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
public class LecturerSubject {

    @EmbeddedId
    private LecturerSubjectId lecturerSubjectId;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    @MapsId("lecturerId")
    private User lecturer;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @MapsId("subjectId")
    private Subject subject;

}
