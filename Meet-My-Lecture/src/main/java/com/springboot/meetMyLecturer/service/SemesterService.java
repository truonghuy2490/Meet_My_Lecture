package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.SemesterResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectSemesterResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.RequestResponse;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SemesterResponse;
import com.springboot.meetMyLecturer.modelDTO.SemesterDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectSemesterDTO;

import java.util.List;

public interface SemesterService {
    // paging semester
    List<SemesterResponseDTO> getAllSemesters();

    List<SemesterResponseDTO> getAllSemestersForAdmin();

    SemesterResponseDTO createSemester(Long adminId, SemesterDTO semesterDTO);

    SemesterResponseDTO editSemester(Long adminId, Long semesterId, SemesterDTO semesterDTO);

    String deleteSemester(Long adminId, Long semesterId);

    SubjectSemesterResponseDTO insertSubjectIntoSemester(Long adminId, SubjectSemesterDTO subjectSemesterDTO);

    SemesterResponse getAllSemesters(int pageNo, int pageSize, String sortBy, String sortDir);
}
