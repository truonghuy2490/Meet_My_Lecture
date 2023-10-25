package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.UserProfileDTO;
import com.springboot.meetMyLecturer.entity.Notification;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.NotificationDTO;
import com.springboot.meetMyLecturer.repository.NotificationRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.NotificationService;
import com.springboot.meetMyLecturer.utils.NotificationType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    MessageChannel notificationChannel;

    @Override
    public List<NotificationDTO> getAllNotificationByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User", "id", String.valueOf(userId))
        );
        List<Notification> notifications = notificationRepository.findNotificationByUser_UserId(user.getUserId());
        List<NotificationDTO> notificationDTOS = notifications.stream().map(
                notification -> modelMapper.map(notification, NotificationDTO.class)
        ).collect(Collectors.toList());
        return notificationDTOS;
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        Notification newNotification = notificationRepository.save(notification);
        return modelMapper.map(newNotification, NotificationDTO.class);
    }

    public void sendNotification(String message) {
//        Message<String> notification = MessageBuilder
//                .withPayload(message)
//                .build();
//        notificationChannel.send(notification);
    }

    // Method to send a notification to a student
    private void sendNotification(UserProfileDTO student, String message, NotificationType notificationType) {
        // Implement the logic to send notifications (e.g., push notifications, emails, etc.)
        // You should specify how notifications are sent based on your system.
        // This method is a placeholder for the actual notification delivery logic.
    }

    // Create and send a notification for request approval or rejection.
    public void sendRequestStatusNotification(UserProfileDTO student, NotificationType notificationType) {
        String message = "Your request has been " + notificationType.toString();
        sendNotification(student, message, notificationType);
    }

    // Create and send a notification for successful request creation.
    public void sendRequestCreationNotification(UserProfileDTO student) {
        String message = "Your request has been created successfully.";
        sendNotification(student, message, NotificationType.RequestCreateSuccessful);
    }

    // Create and send a notification for successful request deletion.
    public void sendRequestDeletionNotification(UserProfileDTO student) {
        String message = "Your request has been deleted successfully.";
        sendNotification(student, message, NotificationType.RequestDeleteSuccessful);
    }

    // Create and send a notification when a room is assigned.
    public void sendRoomAssignedNotification(UserProfileDTO student, EmptySlotDTO emptySlotDTO) {
        String message = "You have been assigned to Room " +
                emptySlotDTO.getRoomId() + " on " + emptySlotDTO.getDateStart() + " at " +
                emptySlotDTO.getTimeStart().toLocalTime() + " for " + emptySlotDTO.getTimeStart().getTime() + " minutes.";
        sendNotification(student, message, NotificationType.RoomAssigned);
    }

    // Create and send a notification when the slot's start time is now.
    public void sendSlotStartNotification(UserProfileDTO user, Date startDate) {
        Instant now = Instant.now(); // Get the current time as an Instant
        Instant startInstant = startDate.toInstant(); // Convert Date to Instant

        if (startInstant.isBefore(now)) {
            String message = "Your slot is starting now.";
            sendNotification(user, message, NotificationType.SlotStart);
        }
    }
}
