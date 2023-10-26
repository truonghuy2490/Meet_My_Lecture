package com.springboot.meetMyLecturer.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "")
public class ReportError {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int reportErrorId;

    private String reportErrorTitle;

    private String reportErrorDescription;
    
}
