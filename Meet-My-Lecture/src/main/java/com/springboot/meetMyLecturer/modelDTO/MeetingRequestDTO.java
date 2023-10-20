package com.springboot.meetMyLecturer.modelDTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MeetingRequestDTO {

    private LocalDate createAt;

    private String requestContent;
}
