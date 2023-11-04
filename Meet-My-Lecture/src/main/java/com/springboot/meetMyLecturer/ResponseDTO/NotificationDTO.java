package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class NotificationDTO {
    private String notificationMessage;

    private String notificationType;

    private LocalDateTime timestamp;
}
