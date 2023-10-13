package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private int absentCount;

    @OneToMany(mappedBy = "user")
    private Set<Major> majors;

    @OneToMany(mappedBy = "user")
    private Set<Semester> semesters;

    @OneToMany(mappedBy = "user")
    private Set<Subject> subjects;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user", nullable = false)
//    private EmptySlot emptySlot;
    @OneToMany(mappedBy = "lecturer")
    private Set<EmptySlot> emptySlots;

    @OneToMany(mappedBy = "student")
    private Set<EmptySlot> bookedSlots;

    @OneToMany(mappedBy = "student")
    private Set<MeetingRequest> meetingRequests;

    @OneToMany(mappedBy = "lecturer")
    private Set<MeetingRequest> requests;

    @OneToMany(mappedBy = "user")
    private Set<Notification> notifications;

    @OneToMany(mappedBy = "lecturer")
    private Set<TeachingSchedule> teachingSchedules;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "lecturer_subject",
            joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    )
    private Set<Subject> subjectSet;

    @OneToMany(mappedBy = "student")
    private Set<SubjectLecturerStudent> subjectLecturerStudentSet1;

    @OneToMany(mappedBy = "lecturer")
    private Set<SubjectLecturerStudent> subjectLecturerStudentSet2;


}
