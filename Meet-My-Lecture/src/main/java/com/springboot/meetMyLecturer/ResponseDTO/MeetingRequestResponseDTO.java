package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MeetingRequestResponseDTO {

    private int meetingRequestId;

    private String studentName;

    private String lecturerName;

    private String subjectId;

    private String requestContent;

    private String requestStatus;

    private LocalDateTime createAt;
}
