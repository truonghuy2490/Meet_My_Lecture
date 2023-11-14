package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class UserMajorId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "major_id")
    private Long majorId;
}
