package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Notification;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.repository.NotificationRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;
    // Send All
    @Override
    public void sendNotification(NotificationDTO notificationDTO) {
        getMessagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
    }
    // Send Specific
    public void sendNotificationToUser(Long userId, NotificationDTO notificationDTO) {
        String destination = "/user/" + getUserNameFromUserId(userId) + "/queue/notifications";
        getMessagingTemplate.convertAndSend(destination, notificationDTO);
    }

    @Override
    public void slotNotification(
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
            sendNotificationToUser(
                    emptySlot.getLecturer().getUserId(),
                    notificationDTO
            );
    }

    @Override
    public void requestNotification(String message, NotificationType type, MeetingRequest meetingRequest) {
        // set entity
        Notification notification = new Notification();
        notification.setNotificationMessage(message);
        notification.setTimestamp(new Date());
        notification.setUser(meetingRequest.getLecturer());
        notification.setMeetingRequest(meetingRequest);

        // convert into dto
        NotificationDTO notificationDTO = mapper.map(notification, NotificationDTO.class);
        notificationDTO.setNotificationType(type.toString());

        // Save the notification to the database
        notificationRepository.save(notification);

        // Call sendNotification with the created notification
        sendNotificationToUser(
                meetingRequest.getStudent().getUserId() ,
                notificationDTO
        );
    }
//    @Scheduled(fixedRate = 6000)
    @Override
    public void schedulingNotification(String message, NotificationType type, EmptySlot emptySlot) {
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

    public String getUserNameFromUserId(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("user", "id", String.valueOf(userId))
        );
        return user.getUserName();
    }
    private void sendNotificationViaWebSocket(NotificationDTO notificationDTO) {
        getMessagingTemplate.convertAndSend("/topic/notifications", notificationDTO);
    }
}
