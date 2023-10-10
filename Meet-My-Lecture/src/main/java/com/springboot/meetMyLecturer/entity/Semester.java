package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "Semesters")
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int semesterId;

    private String semesterName;
    private Date dateStart;
    private Date dateEnd;

}
