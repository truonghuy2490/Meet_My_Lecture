package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;

import java.util.List;

public interface SemesterService {
    List<SemesterDTO> getAllSemester();
    SemesterDTO createSemester(Long adminId, SemesterDTO semesterDTO);
    void deleteSemester(Long semesterId, Long adminId);

    SemesterDTO updateSemester(SemesterDTO semesterDTO, Long semesterId, Long adminID);
}