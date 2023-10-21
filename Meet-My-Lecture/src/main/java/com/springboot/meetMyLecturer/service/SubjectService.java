package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;

import java.util.List;

public interface SubjectService {


    List<LecturerSubjectResponseDTO> searchSubject (String keyword);

    List<LecturerSubjectResponseDTO> getSubjectByMajorId(Long majorId);

}
