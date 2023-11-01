package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectSemesterDTO {
    private Set<String> subjectSet;
    private Long semesterId;
}
