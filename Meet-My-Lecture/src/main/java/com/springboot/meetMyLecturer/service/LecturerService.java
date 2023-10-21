package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import org.springframework.stereotype.Service;


public interface LecturerService {

    String deleteSubjectsForLecturer(Long lecturerId, String subjectId);

    LecturerSubjectResponseDTO insertTaughtSubjects(Long lecturerId, String subjectId);

}
