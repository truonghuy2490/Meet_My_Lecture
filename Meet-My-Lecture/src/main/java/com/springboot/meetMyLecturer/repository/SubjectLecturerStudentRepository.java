package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.SubjectLecturerStudent;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectLecturerStudentRepository extends JpaRepository<SubjectLecturerStudent, SubjectLecturerStudentId> {

    List<SubjectLecturerStudent> searchSubjectLecturerStudentsByStudent_UserIdAndStatus(Long studentId, String status);

    SubjectLecturerStudent searchSubjectLecturerStudentBySubjectLecturerStudentIdAndStatus(SubjectLecturerStudentId subjectLecturerStudentId, String status)
;}
