package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Semester")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "semester_id")
    private int semesterId;

    @Column(name = "semester_name", nullable = false)
    private String semesterName;

    private Date dateStart;

    private Date dateEnd;

    private int year;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subject_semester",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectSet;

    @OneToMany(mappedBy = "semester")
    private Set<WeeklyEmptySlot> weeklyEmptySlots;
}
