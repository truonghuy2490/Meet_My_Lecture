package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}
