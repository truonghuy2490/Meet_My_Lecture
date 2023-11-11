package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface EmptySlotRepository extends JpaRepository<EmptySlot, Long> {
    @Query("select em from EmptySlot em where em.lecturer.userId =:userId and em.status <>'EXPIRED'")
    List<EmptySlot> findEmptySlotsByLecturer_UserId(Long userId);

    List<EmptySlot> findEmptySlotsByStudent_UserId(Long userId);

    List<EmptySlot> findEmptySlotsByWeeklySlot_WeeklySlotId(Long weeklySlotId);

    List<EmptySlot> findEmptySlotsByWeeklySlot_WeeklySlotIdAndLecturer_UserId(Long weeklySlotId, Long lecturerId);

    @Query("select em.emptySlotId from EmptySlot em where em.status =:status")
    List<Long> findEmptySlotsByStatus(String status);
    @Query("select em from EmptySlot em where em.dateStart =:date")
    List<EmptySlot> findEmptySlotByDateStart(Date date);

    @Query("select em from EmptySlot em " +
            "join WeeklyEmptySlot w on w.weeklySlotId = em.weeklySlot.weeklySlotId and (em.student.userId =:userId or em.lecturer.userId =:userId)" +
            "join Semester s on s.semesterId = w.semester.semesterId and s.semesterId =:semesterId")
    List<EmptySlot> findEmptySlotsBySemester(Long semesterId, Long userId);

    Page<EmptySlot> findEmptySlotByStatus(String status, Pageable pageable);
    @Query("select em from EmptySlot em where em.lecturer.userId =:userId and em.status=:status")
    Page<EmptySlot> findEmptySlotByLecturer_UserIdAndStatus(Long userId, String status, Pageable pageable);
    @Query("select em from EmptySlot em where em.lecturer.userId =:userId")
    Page<EmptySlot> findEmptySlotByLecturer_UserId(Pageable pageable, Long userId);
}

