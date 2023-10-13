package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Entity
@Getter
@Setter
@Table(name = "Subject", uniqueConstraints = {
            @UniqueConstraint(
                    name = "subject_name_unique",
                    columnNames = "subject_name")
        }
)
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "subject_name",nullable = false)
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private Set<EmptySlot> emptySlots;

    @ManyToMany(mappedBy = "subjectSet")
    private Set<User> userSet;

    @ManyToMany(mappedBy = "subjectSet")
    private Set<Semester> semesterSet;

    @OneToMany(mappedBy = "subject")
    private Set<SubjectLecturerStudent> subjectLecturerStudentSet;





}
