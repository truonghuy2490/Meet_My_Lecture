package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MeetingRequestForStudentDTO {
    private Long lecturerId;

    private String subjectId;

    private String requestContent;
}
