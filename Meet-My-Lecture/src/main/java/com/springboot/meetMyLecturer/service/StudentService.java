package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.modelDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;

import java.util.List;

public interface StudentService {

    List<UserDTO> searchLecturers (String name);

    List<SubjectResponseDTO> getSubjectByLecturerId(int id);

}
