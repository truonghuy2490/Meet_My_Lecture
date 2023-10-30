package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.MeetingRequest;
import com.springboot.meetMyLecturer.service.impl.MeetingRequestServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, Long> {
    List<MeetingRequest> findMeetingRequestByLecturerUserId(Long lecturerID);

    List<MeetingRequest> findMeetingRequestByStudent_UserId(Long studentId);
    @Query("select rq from MeetingRequest rq where rq.student.userId =:studentId")
    Page<MeetingRequest> findMeetingRequestByStudent_UserId(@Param("studentId")Long studentId, Pageable pageable);
}
