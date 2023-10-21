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

        List<LecturerSubjectResponseDTO> lecturerSubjectResponseDTOList = new ArrayList<>();

        for (int i = 0; i < subjectList.size(); i++){
            List<User> lecturerList = subjectRepository.findLecturerBySubjectId(subjectList.get(i).getSubjectId());
            for(int j = 0; j < lecturerList.size(); j++){
                LecturerSubjectResponseDTO lecturerSubjectResponseDTO = new LecturerSubjectResponseDTO();
                lecturerSubjectResponseDTO.setLecturerId(lecturerList.get(j).getUserId());
                lecturerSubjectResponseDTO.setSubjectId(subjectList.get(i).getSubjectId());
                lecturerSubjectResponseDTO.setLecturerName(lecturerList.get(j).getUserName());
                lecturerSubjectResponseDTO.setUnique(lecturerList.get(j).getUnique());
                lecturerSubjectResponseDTOList.add(lecturerSubjectResponseDTO);
            }
        }
        return lecturerSubjectResponseDTOList;
    }

    //search subject by majorId
    @Override
    public List<LecturerSubjectResponseDTO> getSubjectByMajorId(Long majorId) {
        Major major = majorRepository.findById(majorId).orElseThrow(
                ()-> new ResourceNotFoundException("Major","id",String.valueOf(majorId))
        );

        List<String> subjects = subjectMajorRepository.findSubjectIdByMajorId(majorId);

        List<Subject> subjectList = new ArrayList<>();
       for(int i = 0; i< subjects.size(); i++){
           Subject subject = subjectRepository.findById(subjects.get(i)).orElseThrow();
           subjectList.add(subject);
       }

       List<LecturerSubjectResponseDTO> lecturerSubjectResponseDTOList = new ArrayList<>();
       for(int i =0; i < subjectList.size(); i++){
           List<User> lecturerList = subjectRepository.findLecturerBySubjectId(subjectList.get(i).getSubjectId());
           for(int j = 0; j < lecturerList.size(); j++){
               LecturerSubjectResponseDTO lecturerSubjectResponseDTO = new LecturerSubjectResponseDTO();
               lecturerSubjectResponseDTO.setUnique(lecturerList.get(j).getUnique());
               lecturerSubjectResponseDTO.setLecturerName(lecturerList.get(j).getUserName());
               lecturerSubjectResponseDTO.setSubjectId(subjectList.get(i).getSubjectId());
               lecturerSubjectResponseDTO.setLecturerId(lecturerList.get(j).getUserId());
               lecturerSubjectResponseDTOList.add(lecturerSubjectResponseDTO);
           }
       }
        return lecturerSubjectResponseDTOList;
    }
}
