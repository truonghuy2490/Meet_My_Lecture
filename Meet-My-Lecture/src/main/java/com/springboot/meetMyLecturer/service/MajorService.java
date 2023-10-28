package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.MajorDTO;

import java.util.List;

public interface MajorService {

    List<MajorResponseDTO> getAllMajors();

    List<MajorResponseDTO> getAllMajorsForAdmin();

    MajorResponseDTO createMajor(Long adminId, String majorName);

    MajorResponseDTO editMajor(Long adminId, MajorDTO majorDTO);

}
