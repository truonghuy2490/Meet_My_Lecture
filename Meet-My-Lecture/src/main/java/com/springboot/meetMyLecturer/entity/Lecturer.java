package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "Lecturers", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class Lecturer {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)//chua biet duoc kieu id actor! nen cho random
    private Long lecturerId;

    private String lecturerName;
    private String lecturerEmail;
    private String roleId;
    private String gender;

    public Lecturer( String lecturerName, String lecturerEmail, String roleId, String gender) {

        this.lecturerName = lecturerName;
        this.lecturerEmail = lecturerEmail;
        this.roleId = roleId;
        this.gender = gender;
    }
    public Lecturer() {
    }

}
