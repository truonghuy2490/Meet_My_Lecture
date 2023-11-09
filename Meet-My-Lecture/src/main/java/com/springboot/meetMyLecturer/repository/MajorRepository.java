package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {

    Major findMajorByMajorIdAndStatus(Long majorId, String status);

    Major findMajorByMajorName(String majorName);

    List<Major> findMajorsByStatus(String status);

    Page<Major> findByStatus(String status, Pageable pageable);

    @Query("select sm.major.majorId from SubjectMajor sm where sm.subject.subjectId =:subjectId")
    List<Long> findMajorIdBySubjectId(String subjectId);

}
