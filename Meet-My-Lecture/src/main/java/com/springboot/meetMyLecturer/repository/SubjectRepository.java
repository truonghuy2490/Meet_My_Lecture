package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,String> {

    List<Subject> findSubjectBySubjectIdContainsAndStatus(String keyword, String status);

    @Query("select s from Subject s join LecturerSubject ls on ls.subject.subjectId = s.subjectId and ls.lecturer.userId = :lecturerId where s.status =:status")
    List<Subject> findSubjectsByLecturerIdAndStatus(long lecturerId, String status);

    @Query("select u from User u join LecturerSubject ls on ls.lecturer.userId = u.userId and ls.subject.subjectId = :subjectId where u.status =:status")
    List<User> findLecturerBySubjectIdAndStatus(String subjectId, String status);

    Subject findSubjectBySubjectIdAndStatus(String subjectId, String status);

    @Query("select u from User u join LecturerSubject ls on ls.lecturer.userId = u.userId and ls.subject.subjectId = :subjectId")
    List<User> findLecturerBySubjectId(String subjectId);
    @Query("select s from Subject s where s.subjectId =:subjectId")
    Subject findSubjectNameBySubjectId(String subjectId);

    @Query("select s from Subject s join SubjectMajor sm on sm.subject.subjectId = s.subjectId and sm.major.majorId = :majorId where s.status =:status")
    List<Subject> findSubjectsByMajorId(Long majorId, String status);


}
