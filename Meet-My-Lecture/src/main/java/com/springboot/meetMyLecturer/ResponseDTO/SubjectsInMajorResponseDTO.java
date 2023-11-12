package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.util.Map;

@Data
public class SubjectsInMajorResponseDTO {
    Map<String, String> subjectList;
    Long majorId;
    String majorName;
}
