package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("select nt from Notification nt where nt.user.userId =:userId")
    Page<Notification> findNotificationByByUser_UserId(@Param("userId")Long userId, Pageable pageable);
}
