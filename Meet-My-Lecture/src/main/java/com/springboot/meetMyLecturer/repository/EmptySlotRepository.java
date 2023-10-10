package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmptySlotRepository extends JpaRepository<EmptySlot, Long> {

}
