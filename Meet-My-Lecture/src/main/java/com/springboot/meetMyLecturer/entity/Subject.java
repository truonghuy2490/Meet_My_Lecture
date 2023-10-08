package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "Subjects", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int subjectId;

    private String subjectName;


}
