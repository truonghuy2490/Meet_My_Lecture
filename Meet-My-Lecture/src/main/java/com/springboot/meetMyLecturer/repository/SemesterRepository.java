package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
