package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectResponseForAdminDTO {
    private String subjectId;
    private String subjectName;
    private Set<MajorResponseDTO> major;
    private String status;
}
