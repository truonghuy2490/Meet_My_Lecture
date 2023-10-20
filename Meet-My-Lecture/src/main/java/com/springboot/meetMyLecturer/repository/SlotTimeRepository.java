package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.SlotTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface SlotTimeRepository extends JpaRepository<SlotTime, Integer> {

}
