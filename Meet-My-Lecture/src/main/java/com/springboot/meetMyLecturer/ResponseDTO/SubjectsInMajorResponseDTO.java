package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class SubjectsInMajorResponseDTO {
    Set<SubjectResponseTwoFieldDTO> subjectList;
    Long majorId;
    String majorName;
}
