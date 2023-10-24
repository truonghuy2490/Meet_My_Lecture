package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.util.Set;

@Data
public class SubjectMajorResponseDTO {

    private String subjectId;

    private String subjectName;

    private Set<MajorResponseDTO> major;

    private Long lecturerId;

    private String unique;

    private String lecturerName;

    private String status;

}
