package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

@Data
public class LecturerSubjectResponseDTO {
    private Long lecturerId;
    private String lecturerName;
    private String unique;
    private String subjectId;
}
