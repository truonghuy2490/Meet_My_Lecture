package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MeetingRequestDTO {

    private String status;
    private String requestContent;
}
