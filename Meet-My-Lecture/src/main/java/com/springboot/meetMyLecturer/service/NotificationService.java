package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Notification;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.NotificationResponse;
import com.springboot.meetMyLecturer.utils.NotificationType;

import java.util.List;

public interface NotificationService {

    void sendNotification(NotificationDTO notificationDTO);
    public void sendNotificationToUser(Long userId, NotificationDTO notificationDTO);
    NotificationResponse getAllNotificationByUserId(int pageNo, int pageSize, String sortBy, String sortDir, Long userId);
    void slotNotification(
            String message,
            NotificationType type,
            EmptySlot emptySlot
    );
    void requestNotification(
            String message,
            NotificationType type,
            MeetingRequest meetingRequest
    );
    void schedulingNotification(
            String message,
            NotificationType type,
            EmptySlot emptySlot
    );
}
