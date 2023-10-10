package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "Majors")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int majorId;
    private String majorName;

    public Major(int majorId, String majorName) {
        this.majorId = majorId;
        this.majorName = majorName;
    }

    public Major() {

    }
}
