package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Major")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int majorId;

    @Column(name = "major_name", nullable = false)
    private String majorName;



}
