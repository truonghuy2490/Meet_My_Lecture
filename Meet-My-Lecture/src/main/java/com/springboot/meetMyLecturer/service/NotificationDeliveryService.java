package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.entity.Notification;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationDeliveryService {
    void sendNotification(Notification notification);
    void sendEmailNotification(UserProfileDTO user, String subject, String message);

    void sendBulkEmailNotifications(List<UserProfileDTO> users, String subject, String message);

    void sendEmailWithAttachment(UserProfileDTO user, String subject, String message, File attachment);

    void sendScheduledEmailNotification(UserProfileDTO user, String subject, String message, LocalDateTime sendTime);
}
