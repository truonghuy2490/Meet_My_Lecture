package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "lecturer_subject")
public class LecturerSubject {
    @Id
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private User lecturer;

    @Id
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

}

