package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;

import java.util.List;

public interface SemesterService {
    List<SemesterResponseDTO> getAllSemesters();

    List<SemesterResponseDTO> getAllSemestersForAdmin();
}
