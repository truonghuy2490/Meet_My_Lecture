package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Notification;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.utils.NotificationType;

public interface NotificationService {

    void sendNotification(NotificationDTO notificationDTO);
    void createSlotNotification(
            String message,
            NotificationType type,
            EmptySlot emptySlot
    );

}
