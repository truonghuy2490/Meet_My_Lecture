package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

@Data
public class  SubjectResponseDTO {
    private String subjectId;
    private String subjectName;
    private Long majorId;
    private String status;
}
