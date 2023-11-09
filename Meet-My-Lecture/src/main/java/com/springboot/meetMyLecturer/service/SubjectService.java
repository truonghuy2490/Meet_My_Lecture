package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectMajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectMajorResponseForAdminDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SubjectResponse;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectForAminDTO;

import java.util.List;

public interface SubjectService {


    List<LecturerSubjectResponseDTO> searchSubject (String keyword);

    List<LecturerSubjectResponseDTO> getSubjectByMajorId(Long majorId);

    List<SubjectMajorResponseDTO> getAllSubjects();

    SubjectMajorResponseForAdminDTO createSubject(Long adminId, SubjectForAminDTO subjectDTO);

    SubjectResponseDTO editSubjectsInMajor(Long adminId, String subjectId, Long majorId);

    SubjectResponseDTO getSubjectBySubjectId(String subjectId);

    List<LecturerSubjectResponseDTO> getSubjectsByMajorId(Long majorId);

    SubjectResponse getAllSubjects(int pageNo, int pageSize, String sortBy, String sortDir, String status);

    SubjectResponse getAllSubjectsByMajorId(int pageNo, int pageSize, String sortBy, String sortDir, Long majorId);


}
