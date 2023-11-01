package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.MeetingRequestResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Notification;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.NotificationResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RequestResponse;
import com.springboot.meetMyLecturer.repository.NotificationRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.NotificationService;
import com.springboot.meetMyLecturer.utils.NotificationType;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public NotificationResponse getAllNotificationByUserId(int pageNo, int pageSize, String sortBy, String sortDir, Long userId) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // CREATE PAGEABLE INSTANCE
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        // SAVE TO REPO
        Page<Notification> notifications = notificationRepository.findNotificationByByUser_UserId( userId, pageable); // findAllByStudentId()
        if(notifications.isEmpty()){
            throw new RuntimeException("There are no notification .");
        }

        // get content for page object
        List<Notification> listOfNotification = notifications.getContent();

        List<NotificationDTO> content = listOfNotification.stream().map(request -> mapper.map(request, NotificationDTO.class)).collect(Collectors.toList());

        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setContent(content);
        notificationResponse.setTotalPage(notifications.getTotalPages());
        notificationResponse.setTotalElement(notifications.getTotalElements());
        notificationResponse.setPageNo(notifications.getNumber());
        notificationResponse.setPageSize(notifications.getSize());
        notificationResponse.setLast(notifications.isLast());

        return notificationResponse;
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
        notification.setTimestamp(LocalDate.now() );
        notification.setUser(emptySlot.getLecturer());
        notification.setEmptySlot(emptySlot);
        notification.setNotificationType(type.toString());

        // Save the notification to the database
        notificationRepository.save(notification);

        // Call sendNotification with the created notification
            sendNotificationToUser(
                    emptySlot.getLecturer().getUserId(),
                    mapper.map(notification, NotificationDTO.class)
            );
    }

    @Override
    public void requestNotification(String message, NotificationType type, MeetingRequest meetingRequest) {
        // set entity
        Notification notification = new Notification();
        notification.setNotificationMessage(message);
        notification.setTimestamp(LocalDate.now());
        notification.setUser(meetingRequest.getLecturer());
        notification.setMeetingRequest(meetingRequest);
        notification.setNotificationType(type.toString());

        // Save the notification to the database
        notificationRepository.save(notification);

        // Call sendNotification with the created notification
        sendNotificationToUser(
                meetingRequest.getStudent().getUserId() ,
                mapper.map(notification, NotificationDTO.class)
        );
    }
//    @Scheduled(fixedRate = 6000)
    @Override
    public void schedulingNotification(String message, NotificationType type, EmptySlot emptySlot) {
        // set entity
        Notification notification = new Notification();
        notification.setNotificationMessage(message);
        notification.setTimestamp(LocalDate.now());
        notification.setUser(emptySlot.getLecturer());
        notification.setEmptySlot(emptySlot);
        notification.setNotificationType(type.toString());


        // Save the notification to the database
        notificationRepository.save(notification);

        // Call sendNotification with the created notification
        sendNotificationViaWebSocket(mapper.map(
                notification,
                NotificationDTO.class
        ));

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
