package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectMajorDTO {
    private Set<String> subjectSet;
    private Long majorId;
}
