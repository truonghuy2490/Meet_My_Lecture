package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.SubjectSemester;
import com.springboot.meetMyLecturer.entity.SubjectSemesterId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubjectSemesterRepository extends JpaRepository<SubjectSemester, SubjectSemesterId> {

    SubjectSemester findSubjectSemesterBySubjectSemesterId(SubjectSemesterId subjectSemesterId);

    @Query("select sm.subject.subjectId from SubjectSemester sm where sm.semester.semesterId = :semesterId")
    List<String> findSubjectIdByMajorId(Long semesterId);

}
