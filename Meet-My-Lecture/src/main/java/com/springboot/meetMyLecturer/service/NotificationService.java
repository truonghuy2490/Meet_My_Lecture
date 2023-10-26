package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import com.springboot.meetMyLecturer.modelDTO.NotificationDTO;
import com.springboot.meetMyLecturer.modelDTO.UserRegister;
import com.springboot.meetMyLecturer.utils.NotificationType;

import java.util.List;

public interface NotificationService {
    // Read all notification
    List<NotificationDTO> getAllNotificationByUser(Long userId);
    // create new notification
    NotificationDTO createNotification(NotificationDTO notificationDTO);

    public void sendNotification(String message);

}
