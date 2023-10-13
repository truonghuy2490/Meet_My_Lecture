package com.springboot.meetMyLecturer.modelDTO;

import com.springboot.meetMyLecturer.entity.User;
import lombok.Data;

@Data
public class MeetingRequestDTO {
    private int requestId;

    private String requestContent;

    private String requestStatus;
}
