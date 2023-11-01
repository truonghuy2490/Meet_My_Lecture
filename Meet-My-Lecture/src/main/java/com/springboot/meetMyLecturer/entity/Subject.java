package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Subject", uniqueConstraints = {
            @UniqueConstraint(
                    name = "subject_name_unique",
                    columnNames = "subject_name")
        }
)
public class Subject {
    @Id
    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "subject_name",nullable = false)
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    /*@ManyToMany(mappedBy = "subjectSet",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Major> majorSet = new HashSet<>();*/

    /*@ManyToMany(mappedBy = "subjectSet", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Semester> semesterSet = new HashSet<>();*/

    @JoinColumn(name = "status")
    private String status;

}
