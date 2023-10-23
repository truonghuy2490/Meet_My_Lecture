package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.WeeklyEmptySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WeeklySlotRepository extends JpaRepository<WeeklyEmptySlot, Long> {


}
