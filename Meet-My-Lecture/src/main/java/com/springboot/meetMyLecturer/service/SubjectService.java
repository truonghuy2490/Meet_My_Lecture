package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectResponseDTO;

import java.util.List;

public interface SubjectService {


    List<SubjectDTO> searchSubject (String keyword);


}
