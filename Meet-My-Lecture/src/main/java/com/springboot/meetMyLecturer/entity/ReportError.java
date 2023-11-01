package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_error")
public class ReportError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_error_id")
    private Long reportErrorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String reportErrorContent;

    private String status;

    private Date createAt;
}
