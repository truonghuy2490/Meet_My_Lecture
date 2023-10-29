package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import lombok.Data;

@Data
public class SubjectLecturerStudentDTO {
    private SubjectLecturerStudentId subjectLecturerStudentId;

    private String subjectIdNew;

    private Long lecturerIdNew;

}
