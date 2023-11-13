package com.springboot.meetMyLecturer.ResponseDTO;

import com.springboot.meetMyLecturer.entity.Subject;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class SubjectSemesterResponseDTO {
    private Set<SubjectResponseTwoFieldDTO> subjectList;
    private Long semesterId;
    private String semesterName;
}
