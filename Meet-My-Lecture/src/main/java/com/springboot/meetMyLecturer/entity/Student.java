package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "Students", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;

    private String studentName;
    private String studentEmail;
    private String roleId;
    private int absentCount;

    public Student(String studentName, String studentEmail, String roleId, int absentCount) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.roleId = roleId;
        this.absentCount = absentCount;
    }

    public Student() {

    }
}
