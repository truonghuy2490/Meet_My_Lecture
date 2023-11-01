package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;

import java.util.List;

public interface SemesterService {
    List<SemesterResponseDTO> getAllSemesters();

    List<SemesterResponseDTO> getAllSemestersForAdmin();

    SemesterResponseDTO createSemester(Long adminId, SemesterDTO semesterDTO);

    SemesterResponseDTO editSemester(Long adminId, Long semesterId, SemesterDTO semesterDTO);

    String deleteSemester(Long adminId, Long semesterId);
}
