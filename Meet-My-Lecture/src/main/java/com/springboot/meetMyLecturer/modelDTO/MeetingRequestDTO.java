package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

@Data
public class MeetingRequestDTO {

    private int meetingRequestId;

    private UserDTO student;

    private UserDTO lecturer;

    private SubjectResponseRequestDTO subject;

    private String requestContent;

    private String requestStatus;
}
