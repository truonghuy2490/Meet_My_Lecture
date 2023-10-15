package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectResponseRequestDTO;

import java.util.List;

public interface SubjectService {


    List<SubjectDTO> searchSubject (String keyword);

    List<SubjectResponseRequestDTO> getSubjectByLecturerId(int id);


}
