package com.springboot.meetMyLecturer.controller;


import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import com.springboot.meetMyLecturer.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    ModelMapper mapper;
    @MessageMapping("/sendNotification")
    @SendTo("/topic/notifications")
    public NotificationDTO sendNotification(NotificationDTO notificationDTO) {
        notificationService.sendNotification(notificationDTO);
        return notificationDTO;
    }

}
