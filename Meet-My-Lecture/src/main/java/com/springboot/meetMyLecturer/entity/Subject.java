package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private String subjectId;

    @Column(name = "subject_name",nullable = false)
    private String subjectName;

    @Column(name = "major_id",nullable = false)
    private  int majorId;

    @Column(name = "admin_id",nullable = false)
    private  int adminId;



}
