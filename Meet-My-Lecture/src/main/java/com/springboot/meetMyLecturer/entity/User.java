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
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

//    @Column(name = "nick_name")
//    private String nickName;

    private int absentCount;

    @OneToMany(mappedBy = "admin")
    private Set<Major> majors;

    @OneToMany(mappedBy = "user")
    private Set<Semester> semesters;

    @OneToMany(mappedBy = "admin")
    private Set<Subject> subjects;

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
            joinColumns = @JoinColumn(name = "lecturer_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectSet;

    /*@OneToMany(mappedBy = "lecturer")
    private Set<SubjectLecturerStudent> subjectLecturerSet;

    @OneToMany(mappedBy = "student")
    private Set<SubjectLecturerStudent> subjectStudentSet;*/





}
