package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.ReportError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportErrorRepository extends JpaRepository<ReportError, Long> {

    

}
