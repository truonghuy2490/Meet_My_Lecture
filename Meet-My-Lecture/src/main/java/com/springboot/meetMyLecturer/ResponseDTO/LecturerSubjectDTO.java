package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

@Data
public class LecturerSubjectDTO {
    private Long lecturerId;
    private String lecturerName;
    private String nickName;
    private String subjectId;
}
