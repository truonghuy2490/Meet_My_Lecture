package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.LecturerSubject;
import com.springboot.meetMyLecturer.entity.LecturerSubjectId;
import com.springboot.meetMyLecturer.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerSubjectRepository extends JpaRepository<LecturerSubject, LecturerSubjectId> {

    LecturerSubject findLecturerSubjectByLecturerSubjectId(LecturerSubjectId lecturerSubjectId);

    @Query("select ls.subject from LecturerSubject ls where ls.lecturer.userId =:lecturerId and ls.status =:status")
    List<Subject> findSubjectByLecturerId(Long lecturerId, String status);


}
