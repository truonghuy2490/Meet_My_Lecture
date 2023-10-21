package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    SubjectMajorRepository subjectMajorRepository;


    // search subject by subjectId DONE
    @Override
    public List<LecturerSubjectResponseDTO> searchSubject(String keyword) {
        List<Subject> subjectList = subjectRepository.findSubjectBySubjectIdContains(keyword);

        if(subjectList.isEmpty()){
            throw  new RuntimeException("There are no subjects with this name:" + keyword);
        }

        return subjectList.stream()
                .flatMap(subject -> {
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectId(subject.getSubjectId());
                    return lecturerList.stream().map(lecturer -> {
                        LecturerSubjectResponseDTO dto = new LecturerSubjectResponseDTO();
                        dto.setLecturerId(lecturer.getUserId());
                        dto.setSubjectId(subject.getSubjectId());
                        dto.setLecturerName(lecturer.getUserName());
                        dto.setUnique(lecturer.getUnique());
                        return dto;
                    });
                }).collect(Collectors.toList());
    }

    //search subject by majorId
    @Override
    public List<LecturerSubjectResponseDTO> getSubjectByMajorId(Long majorId) {
        Major major = majorRepository.findById(majorId).orElseThrow(
                ()-> new ResourceNotFoundException("Major","id",String.valueOf(majorId))
        );

        List<String> subjects = subjectMajorRepository.findSubjectIdByMajorId(major.getMajorId());

        List<Subject> subjectList = subjects.stream()
                .map(subjectId -> subjectRepository.findById(subjectId).orElseThrow())
                .toList();

        return subjectList.stream()
                .flatMap(subject -> {
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectId(subject.getSubjectId());
                    return lecturerList.stream()
                            .map(lecturer -> {
                                LecturerSubjectResponseDTO lecturerSubjectResponseDTO = new LecturerSubjectResponseDTO();
                                lecturerSubjectResponseDTO.setUnique(lecturer.getUnique());
                                lecturerSubjectResponseDTO.setLecturerName(lecturer.getUserName());
                                lecturerSubjectResponseDTO.setSubjectId(subject.getSubjectId());
                                lecturerSubjectResponseDTO.setLecturerId(lecturer.getUserId());
                                return lecturerSubjectResponseDTO;
                            });
                }).collect(Collectors.toList());
    }
}
