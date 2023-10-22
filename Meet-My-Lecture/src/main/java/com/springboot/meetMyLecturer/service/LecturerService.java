package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.LecturerSubjectId;
import org.springframework.stereotype.Service;


public interface LecturerService {

    String deleteSubjectsForLecturer(LecturerSubjectId lecturerSubjectId);

    LecturerSubjectResponseDTO insertTaughtSubjects(LecturerSubjectId lecturerSubjectId);

}
