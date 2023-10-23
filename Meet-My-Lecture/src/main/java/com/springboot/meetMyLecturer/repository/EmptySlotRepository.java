package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmptySlotRepository extends JpaRepository<EmptySlot, Long> {
    List<EmptySlot> findEmptySlotsByLecturer_UserId(Long userId);

    List<EmptySlot> findEmptySlotsByStudent_UserId(Long userId);

    List<EmptySlot> findEmptySlotsByWeeklySlot_WeeklySlotId(Long weeklySlotId);

    List<EmptySlot> findEmptySlotsByWeeklySlot_WeeklySlotIdAndLecturer_UserId(Long weeklySlotId, Long lecturerId);


}
