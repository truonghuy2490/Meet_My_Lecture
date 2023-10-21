package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class MeetingRequestDTO {

    private LocalDateTime createAt;

    private String requestContent;
}
