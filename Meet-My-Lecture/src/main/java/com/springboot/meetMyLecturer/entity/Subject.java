package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(
        name = "Subjects",
        uniqueConstraints = {
            @UniqueConstraint(
                    name = "subject_name",
                    columnNames = "name")
        }
)
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int subjectId;

    @Column(name = "subject_name",nullable = false)
    private String subjectName;


}
