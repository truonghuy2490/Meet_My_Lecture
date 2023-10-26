package com.springboot.meetMyLecturer.ResponseDTO;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDTO {
    private String notificationMessage;

    private String notificationType;

    private Date timestamp;
}
