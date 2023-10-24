package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.MajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectMajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    SubjectMajorRepository subjectMajorRepository;

    @Autowired
    MajorRepository majorRepository;


    // search subject by subjectId DONE - DONE
    @Override
    public List<LecturerSubjectResponseDTO> searchSubject(String keyword) {
        List<Subject> subjectList = subjectRepository.findSubjectBySubjectIdContainsAndStatus(keyword, Constant.OPEN);

        if(subjectList.isEmpty()){
            throw  new RuntimeException("There are no subjects with this name:" + keyword);
        }

        return subjectList.stream()
                .flatMap(subject -> {
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectIdAndStatus(subject.getSubjectId(), Constant.OPEN);
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

    //search subject by majorId DONE - DONE
    @Override
    public List<LecturerSubjectResponseDTO> getSubjectByMajorId(Long majorId) {
        Major major = majorRepository.findMajorByMajorIdAndStatus(majorId, Constant.OPEN);
        if (major == null){
            throw new RuntimeException("This major is not existed.");
        }

        List<String> subjects = subjectMajorRepository.findSubjectIdByMajorId(major.getMajorId());

        List<Subject> subjectList = subjects.stream()
                .map(subject -> subjectRepository.findSubjectBySubjectIdAndStatus(subject, Constant.OPEN))
                .filter(Objects::nonNull)
                .toList();

        return subjectList.stream()
                .flatMap(subject -> {
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectIdAndStatus(subject.getSubjectId(), Constant.OPEN);
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

    //get all subjects for amin DONE - DONE
    @Override
    public List<SubjectMajorResponseDTO> getAllSubjects() {
        List<Subject> subjectList = subjectRepository.findAll();
        if (subjectList.isEmpty()) {
            throw new RuntimeException("There are no subjects");
        }

        List<SubjectMajorResponseDTO> result = subjectList.stream()
                .flatMap(subject -> {
                    List<User> lecturerList = subjectRepository.findLecturerBySubjectId(subject.getSubjectId());
                    List<Long> majorList = subjectMajorRepository.findMajorIdBySubjectId(subject.getSubjectId());

                    Set<MajorResponseDTO> majorSet = majorList.stream()
                            .map(majorId -> majorRepository.findById(majorId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Major", "id", String.valueOf(majorId)))
                            )
                            .map(major -> modelMapper.map(major, MajorResponseDTO.class))
                            .collect(Collectors.toSet());

                    return lecturerList.stream()
                            .map(lecturer -> {
                                SubjectMajorResponseDTO dto = new SubjectMajorResponseDTO();
                                dto.setLecturerId(lecturer.getUserId());
                                dto.setSubjectId(subject.getSubjectId());
                                dto.setSubjectName(subject.getSubjectName());
                                dto.setLecturerName(lecturer.getUserName());
                                dto.setUnique(lecturer.getUnique());
                                dto.setStatus(subject.getStatus());
                                dto.setMajor(majorSet);
                                return dto;
                            });
                }).collect(Collectors.toList());

        return result;
    }

    //create subject for admin DONE - DONE
    @Override
    public SubjectResponseDTO createSubject(SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setSubjectId(subjectDTO.getSubjectId());
        subject.setStatus(Constant.OPEN);
        subjectRepository.save(subject);
        return modelMapper.map(subject, SubjectResponseDTO.class);
    }
}
