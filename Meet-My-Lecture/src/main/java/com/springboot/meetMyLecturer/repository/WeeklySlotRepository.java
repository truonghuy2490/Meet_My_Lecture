package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.WeeklyEmptySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeeklySlotRepository extends JpaRepository<WeeklyEmptySlot, Long> {
    //find by first day of week
    @Query("select w from WeeklyEmptySlot w where ?1 between w.firstDayOfWeek and w.lastDayOfWeek")
    WeeklyEmptySlot findWeeklyEmptySlotByFirstDayOfWeek(Date date);
}
