package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.WeeklyEmptySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklySlotRepository extends JpaRepository<WeeklyEmptySlot, Long> {
}
