package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Major")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "major_name", nullable = false)
    private String majorName;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subject_major",
            joinColumns = @JoinColumn(name = "major_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectSet;

    @OneToMany(mappedBy = "major")
    private Set<User> student;




}
