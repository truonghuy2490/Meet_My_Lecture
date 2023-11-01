package com.springboot.meetMyLecturer.ResponseDTO;

import com.springboot.meetMyLecturer.entity.Subject;
import lombok.Data;

import java.util.Map;

@Data
public class SubjectSemesterResponseDTO {
    private Map<String, String> subjectList;
    private Long semesterId;
    private String semesterName;
}
