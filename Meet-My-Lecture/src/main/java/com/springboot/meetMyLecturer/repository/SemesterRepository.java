package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Semester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    @Query("select s from Semester s where ?1 between s.dateStart and s.dateEnd")
    Semester findSemesterByDateStart(Date date);

    Semester findSemesterBySemesterIdAndStatus(Long semesterId, String status);

    List<Semester> findSemestersByStatus(String status);

    Page<Semester> findByStatus(String status, Pageable pageable);

}
