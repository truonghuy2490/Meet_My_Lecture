package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.SubjectSemester;
import com.springboot.meetMyLecturer.entity.SubjectSemesterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectSemesterRepository extends JpaRepository<SubjectSemester, SubjectSemesterId> {

    @Query("select sm.subjectId from SubjectMajor sm where sm.majorId =: majorId")
    List<String> findSubjectIdByMajorId(Long majorId);

}
