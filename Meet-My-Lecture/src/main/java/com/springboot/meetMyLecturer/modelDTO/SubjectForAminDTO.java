package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectForAminDTO {
    private String subjectId;
    private String subjectName;
    private Set<Long> majorId;
}
