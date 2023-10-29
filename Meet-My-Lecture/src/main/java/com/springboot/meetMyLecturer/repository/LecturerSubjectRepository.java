package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.LecturerSubject;
import com.springboot.meetMyLecturer.entity.LecturerSubjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerSubjectRepository extends JpaRepository<LecturerSubject, LecturerSubjectId> {

    LecturerSubject findLecturerSubjectByLecturerSubjectId(LecturerSubjectId lecturerSubjectId);

}
