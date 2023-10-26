package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody String message) {
        notificationService.sendNotification(message);
    }
}