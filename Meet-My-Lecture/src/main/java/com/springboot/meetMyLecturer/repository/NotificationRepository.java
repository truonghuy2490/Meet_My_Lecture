package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findNotificationByUser_UserId(Long userId);
}
