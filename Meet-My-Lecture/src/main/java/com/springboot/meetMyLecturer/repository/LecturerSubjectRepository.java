package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.LecturerSubject;
import com.springboot.meetMyLecturer.entity.LecturerSubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerSubjectRepository extends JpaRepository<LecturerSubject, LecturerSubjectId> {

    LecturerSubject findLecturerSubjectByLecturerIdAndSubjectId(Long lecturerId, String studentId);

}
