package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.SubjectSemester;
import com.springboot.meetMyLecturer.entity.SubjectSemesterId;
import org.springframework.data.jpa.repository.JpaRepository;s
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectSemesterRepository extends JpaRepository<SubjectSemester, SubjectSemesterId> {

}
