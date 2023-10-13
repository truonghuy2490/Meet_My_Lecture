package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
@Entity
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

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "subject_semester",
            joinColumns = @JoinColumn(name = "semester_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectSet;

}
