package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.TeachingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeachingScheduleRepository extends JpaRepository<TeachingSchedule, Long> {

    List<TeachingSchedule> getTeachingScheduleListByLecturer_UserId(Long lecturerId);
}
