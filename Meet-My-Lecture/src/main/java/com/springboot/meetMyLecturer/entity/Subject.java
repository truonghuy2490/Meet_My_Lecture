package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private String subjectId;

    @Column(name = "subject_name",nullable = false)
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "subjectSet")
    private Set<Major> majorSet;

    @OneToMany(mappedBy = "subject")
    private Set<EmptySlot> emptySlots;

    @ManyToMany(mappedBy = "subjectSet")
    private Set<User> userSet;

    @ManyToMany(mappedBy = "subjectSet")
    private Set<Semester> semesterSet;

    @OneToMany(mappedBy = "subject")
    private Set<SubjectLecturerStudent> subjectLecturerStudentSet;

    @OneToMany(mappedBy = "subject")
    private Set<MeetingRequest> meetingRequests;

}
