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
@Table(name = "Lecturers", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int notiId;
    private String notiContent;
    private Date notiDate;

}
