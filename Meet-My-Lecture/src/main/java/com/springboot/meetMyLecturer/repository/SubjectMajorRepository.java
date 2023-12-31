package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.entity.SubjectMajor;
import com.springboot.meetMyLecturer.entity.SubjectMajorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubjectMajorRepository extends JpaRepository<SubjectMajor, SubjectMajorId> {

    @Query("select sm.subject.subjectId from SubjectMajor sm where sm.major.majorId = :majorId")
    List<String> findSubjectIdByMajorId(Long majorId);

    SubjectMajor findSubjectMajorBySubjectMajorId(SubjectMajorId subjectMajorId);

    Set<SubjectMajor> findSubjectMajorsByMajor_MajorId(Long majorId);

}
