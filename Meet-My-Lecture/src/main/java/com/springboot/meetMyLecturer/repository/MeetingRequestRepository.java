package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, Long> {
    List<MeetingRequest> findMeetingRequestByLecturerUserId(Long lecturerID);

    List<MeetingRequest> findMeetingRequestByStudent_UserId(Long studentId);

}
