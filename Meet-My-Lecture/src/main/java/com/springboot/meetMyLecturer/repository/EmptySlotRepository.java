package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmptySlotRepository extends JpaRepository<EmptySlot, Long> {

    @Query("select e from EmptySlot e where e.student.userId = :userId")
    List<EmptySlot> findEmptySlotsByUser_UserId(Long userId);
    List<EmptySlot> findEmptySlotsByLecturer_UserId(Long userId);


}
