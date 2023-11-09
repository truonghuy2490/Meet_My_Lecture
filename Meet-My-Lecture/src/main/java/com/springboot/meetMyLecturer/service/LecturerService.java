package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.LecturerSubjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface LecturerService {

    String deleteSubjectsForLecturer(LecturerSubjectId lecturerSubjectId);

    List<LecturerSubjectResponseDTO> insertTaughtSubjects(Set<LecturerSubjectId> lecturerSubjectId);

    List<LecturerSubjectResponseDTO> getAllSubjects(Long lecturerId);

}
