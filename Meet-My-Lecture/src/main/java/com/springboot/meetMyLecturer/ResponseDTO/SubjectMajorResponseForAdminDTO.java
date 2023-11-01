package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectMajorResponseForAdminDTO {
    private String subjectId;
    private String subjectName;
    private Set<Long> majorId;
}
