package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {

    Major findMajorByMajorIdAndStatus(Long majorId, String status);

    Major findMajorByMajorName(String majorName);

}
