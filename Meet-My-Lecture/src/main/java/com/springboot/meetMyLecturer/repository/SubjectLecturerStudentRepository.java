package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.SubjectLecturerStudent;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectLecturerStudentRepository extends JpaRepository<SubjectLecturerStudent, SubjectLecturerStudentId> {
}
