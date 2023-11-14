package com.springboot.meetMyLecturer.ResponseDTO;


import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class SubjectSemesterResponseDTO {
    private Set<SubjectResponseTwoFieldDTO> subjectList;
    private Long semesterId;
    private String semesterName;
}
