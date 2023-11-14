package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,String> {

    @Query("select s from Subject s where s.subjectId like %:keyword% or s.subjectName like %:keyword% and s.status =:status")
    List<Subject> findSubjectBySubjectIdContainsAndStatus(String keyword, String status);

    @Query("select s from Subject s join LecturerSubject ls on ls.subject.subjectId = s.subjectId and ls.lecturer.userId = :lecturerId  where ls.status =:status")
    List<Subject> findSubjectsByLecturerIdAndStatus(long lecturerId, String status);

    @Query("select u from User u join LecturerSubject ls on ls.lecturer.userId = u.userId and ls.subject.subjectId = :subjectId where u.status =:status")
    List<User> findLecturerBySubjectIdAndStatus(String subjectId, String status);

    Subject findSubjectBySubjectIdAndStatus(String subjectId, String status);

    @Query("select u from User u join LecturerSubject ls on ls.lecturer.userId = u.userId and ls.subject.subjectId = :subjectId")
    List<User> findLecturerBySubjectId(String subjectId);
    @Query("select s from Subject s where s.subjectId =:subjectId")
    Subject findSubjectBySubjectId(String subjectId);

    @Query("select s from Subject s join SubjectMajor sm on sm.subject.subjectId = s.subjectId and sm.major.majorId = :majorId where s.status =:status")
    List<Subject> findSubjectsByMajorId(Long majorId, String status);

    @Query("SELECT s FROM Subject s JOIN SubjectMajor sm ON sm.major.majorId = :majorId AND sm.status =:status")
    Page<Subject> findSubjectsByMajorIdAndStatus(Pageable pageable, Long majorId, String status);

    @Query("SELECT s FROM Subject s JOIN SubjectMajor sm ON sm.major.majorId = :majorId and sm.subject.subjectId = s.subjectId")
    Page<Subject> findSubjectsByMajorIdAndStatus(Pageable pageable, Long majorId);

    Page<Subject> findSubjectByStatus(String status, Pageable pageable);

    List<Subject> findSubjectsByStatus(String status);

    @Query("select m from Major m join SubjectMajor sm on sm.major.majorId = m.majorId and sm.subject.subjectId =:subjectId")
    Set<Major> getMajorNamesForSubject(String subjectId);

    @Query("SELECT s FROM Subject s WHERE  s.subjectName LIKE %:keyword% or s.subjectId like %:keyword%")
    List<Subject> findSubjects(String keyword);





}
