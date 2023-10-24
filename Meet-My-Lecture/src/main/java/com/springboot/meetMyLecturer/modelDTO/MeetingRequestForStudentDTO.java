package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

@Data
public class MeetingRequestForStudentDTO {
    private Long lecturerId;

    private String subjectId;

    private String requestContent;
}
