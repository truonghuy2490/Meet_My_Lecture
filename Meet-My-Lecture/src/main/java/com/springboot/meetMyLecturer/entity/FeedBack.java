package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "Feedbacks", uniqueConstraints = {
        @UniqueConstraint(columnNames = ("")),
        @UniqueConstraint(columnNames = (""))
})
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int feedbackId;
    private String feedbackContent;
}
