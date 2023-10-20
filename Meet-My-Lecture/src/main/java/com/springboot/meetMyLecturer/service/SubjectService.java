package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;

import java.util.List;

public interface SubjectService {


    List<LecturerSubjectDTO> searchSubject (String keyword);



}
