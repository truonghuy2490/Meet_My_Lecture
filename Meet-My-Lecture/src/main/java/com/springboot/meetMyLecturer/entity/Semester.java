package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Getter
@Setter
@Table(name = "Semesters", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int semesterId;
    private String semesterName;
    private Date dateStart;
    private Date dateEnd;

}
