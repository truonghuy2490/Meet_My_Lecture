package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.TeachingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface TeachingScheduleRepository extends JpaRepository<TeachingSchedule, Long> {

    List<TeachingSchedule> getTeachingScheduleListByLecturer_UserId(Long lecturerId);

    @Query("select t.teachingScheduleId from TeachingSchedule t where t.lecturer.userId =:lecturerId and t.status =:status")
    List<Long> getIdByLecturerIdAndStatus(Long lecturerId, String status);

    @Query("select t.teachingScheduleId from TeachingSchedule t where t.date =:date")
    Long findIdByDate(Date date);
}
