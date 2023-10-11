package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "Lecturers")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int notiId;

    private int userId;

    private int slotId;

    private String notiContent;

    private Date notiDate;

}
