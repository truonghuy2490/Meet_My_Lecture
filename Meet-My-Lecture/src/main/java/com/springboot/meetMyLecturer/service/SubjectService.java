package com.springboot.meetMyLecturer.service;

import com.springboot.meetMyLecturer.ResponseDTO.*;
import com.springboot.meetMyLecturer.entity.SubjectMajorId;
import com.springboot.meetMyLecturer.modelDTO.ResponseDTO.SubjectResponse;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectForAminDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SubjectService {


    List<LecturerSubjectResponseDTO> searchSubject (String keyword);

    List<LecturerSubjectResponseDTO> getSubjectsByMajorId(Long majorId);

    SubjectMajorResponseForAdminDTO createSubject(Long adminId, SubjectForAminDTO subjectDTO);

    SubjectResponseDTO getSubjectBySubjectId(String subjectId);

    SubjectResponse getAllSubjects(int pageNo, int pageSize, String sortBy, String sortDir, String status);

    SubjectResponse getAllSubjectsByMajorId(int pageNo, int pageSize, String sortBy, String sortDir, Long majorId, String status);

    String deleteSubjectInMajor(Long adminId, SubjectMajorId subjectMajorId);

    LecturersMajorsResponseDTO getLecturersAndMajorsBySubjectId(String subjectId);

    List<SubjectResponseDTO> searchSubjectForAdmin(String subjectId, String subjectName);

    Set<SubjectResponseTwoFieldDTO> getSubjectsByMajorIdForLec(Long majorId);

}
