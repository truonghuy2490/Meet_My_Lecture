package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectsInMajorResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.MajorDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.MajorResponse;
import com.springboot.meetMyLecturer.modelDTO.SubjectMajorDTO;

import java.util.List;

public interface MajorService {

    MajorResponseDTO createMajor(Long adminId, String majorName);

    MajorResponseDTO editMajor(Long adminId, MajorDTO majorDTO);

    MajorResponse getAllMajors(int pageNo, int pageSize, String sortBy, String sortDir, String status);

    MajorResponseDTO getMajorByMajorId(Long MajorId);

    List<MajorResponseDTO> searchMajor(String majorName);

    SubjectsInMajorResponseDTO insertSubjectsIntoMajor(Long adminId, SubjectMajorDTO subjectMajorDTO);

}
