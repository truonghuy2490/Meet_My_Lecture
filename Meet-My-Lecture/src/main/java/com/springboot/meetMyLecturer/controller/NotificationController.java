package com.springboot.meetMyLecturer.controller;


import com.springboot.meetMyLecturer.ResponseDTO.NotificationDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper mapper;
    @MessageMapping("/sendNotification")
    @SendTo("/topic/notifications")
    public NotificationDTO sendNotification(NotificationDTO notificationDTO) {
        notificationService.sendNotification(notificationDTO);
        return notificationDTO;
    }
    @MessageMapping("/sendNotificationToStudent")
    @SendTo("/topic/notifications/students")
    public void sendNotificationToUser(NotificationDTO notificationDTO, Authentication authentication) {
        Long userId = userRepository.findUserIdByEmail(Constant.EMAIL);
        if(userHasRole(authentication, Constant.STUDENT)) {
            notificationService.sendNotificationToUser(userId, notificationDTO);
        }

    }
    public boolean userHasRole(Authentication authentication, String role){
        Collection<?> roleCollection = authentication.getAuthorities();
            return roleCollection
                    .stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.equals(role));
    }
}
