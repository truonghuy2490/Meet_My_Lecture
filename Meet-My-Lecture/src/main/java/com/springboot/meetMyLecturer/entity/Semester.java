package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "Semester")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int semesterId;

    @Column(name = "semester_name", nullable = false)
    private String semesterName;

    private Date dateStart;

    private Date dateEnd;

    @Column(name = "admin_id", nullable = false)
    private int adminId;

}
