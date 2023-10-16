package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.util.Date;

@Data
public class MeetingRequestDTO {

    private int meetingRequestId;

    private UserDTO student;

    private UserDTO lecturer;

    private SubjectResponseDTO subject;

    private String requestContent;

    private String requestStatus;

    private Date createAt;
}
