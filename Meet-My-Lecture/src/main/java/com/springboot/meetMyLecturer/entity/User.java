package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Major> majors;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Semester> semesters;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private Set<EmptySlot> emptySlots;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<EmptySlot> bookedSlots;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<MeetingRequest> meetingRequests;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private Set<MeetingRequest> requests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Notification> notifications;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private Set<TeachingSchedule> teachingSchedules;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "lecturer_subject",
            joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    )
    private Set<Subject> subjectSet;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<SubjectLecturerStudent> subjectLecturerStudentSet1;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private Set<SubjectLecturerStudent> subjectLecturerStudentSet2;


}
