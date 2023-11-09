package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.MajorDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.MajorResponse;

import java.util.List;

public interface MajorService {

    List<MajorResponseDTO> getAllMajors();

    List<MajorResponseDTO> getAllMajorsForAdmin();

    MajorResponseDTO createMajor(Long adminId, String majorName);

    MajorResponseDTO editMajor(Long adminId, MajorDTO majorDTO);

    MajorResponse getAllMajors(int pageNo, int pageSize, String sortBy, String sortDir, String status);

}
