package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.modelDTO.MeetingRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, Long> {
    List<MeetingRequest> findMeetingRequestByLecturerUserId(Long lecturerID);
}
