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
    private int majorId;

    @Column(name = "major_name", nullable = false)
    private String majorName;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL)
    private Set<Subject> subjects;




}
