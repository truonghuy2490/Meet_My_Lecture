package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,String> {

    List<Subject> findSubjectBySubjectIdContains(String keyword);

    @Query("select s from Subject s join LecturerSubject ls on ls.subject.subjectId = s.subjectId and ls.lecturer.userId = :id ")
    List<Subject> findSubjectsByLecturerId(long id);

    @Query("select u from User u join LecturerSubject ls on ls.lecturer.userId = u.userId and ls.subject.subjectId = :subjectId")
    List<User> findLecturerBySubjectId(String subjectId);

}
