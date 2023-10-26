package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.entity.Notification;
import com.springboot.meetMyLecturer.service.NotificationDeliveryService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationDeliveryServiceImpl implements NotificationDeliveryService {
    @Override
    public void sendNotification(Notification notification) {

    }

    @Override
    public void sendEmailNotification(UserProfileDTO user, String subject, String message) {

    }

    @Override
    public void sendBulkEmailNotifications(List<UserProfileDTO> users, String subject, String message) {

    }

    @Override
    public void sendEmailWithAttachment(UserProfileDTO user, String subject, String message, File attachment) {

    }

    @Override
    public void sendScheduledEmailNotification(UserProfileDTO user, String subject, String message, LocalDateTime sendTime) {

    }
}
