package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, Long> {
}
