package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingRequestResponseDTO {

    private int meetingRequestId;

    private String studentName;

    private int studentId;

    private String lecturerName;

    private String subjectId;

    private String requestContent;

    private String requestStatus;

    private LocalDateTime createAt;
}
