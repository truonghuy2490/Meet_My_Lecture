package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Notification;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.repository.NotificationRepository;
import com.springboot.meetMyLecturer.service.NotificationService;
import com.springboot.meetMyLecturer.utils.NotificationType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    SimpMessagingTemplate getMessagingTemplate;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public void sendNotification(NotificationDTO notificationDTO) {
        getMessagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
    }

    @Override
    public void createSlotNotification(
            String message,
            NotificationType type,
            EmptySlot emptySlot)
    {
        // set entity
        Notification notification = new Notification();
        notification.setNotificationMessage(message);
        notification.setTimestamp(new Date());
        notification.setUser(emptySlot.getLecturer());
        notification.setEmptySlot(emptySlot);

        // convert into dto
        NotificationDTO notificationDTO = mapper.map(notification, NotificationDTO.class);
        notificationDTO.setNotificationType(type.toString());

        // Save the notification to the database
        notificationRepository.save(notification);

        // Call sendNotification with the created notification
        sendNotificationViaWebSocket(notificationDTO);
    }
    private void sendNotificationViaWebSocket(NotificationDTO notificationDTO) {
        getMessagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
    }
}
