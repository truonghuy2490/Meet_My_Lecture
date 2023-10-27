package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
