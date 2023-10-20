package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.Major;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.repository.MajorRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.SubjectSemesterRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
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
    SubjectSemesterRepository subjectSemesterRepository;


    // search subject by subjectId DONE
    @Override
    public List<LecturerSubjectDTO> searchSubject(String keyword) {
        List<Subject> subjectList = subjectRepository.findSubjectBySubjectIdContains(keyword);

        if(subjectList.isEmpty()){
            throw  new RuntimeException("There are no subjects with this name:" + keyword);
        }

        List<LecturerSubjectDTO> lecturerSubjectDTOList = new ArrayList<>();

        for (int i = 0; i < subjectList.size(); i++){
            List<User> lecturerList = subjectRepository.findLecturerBySubjectId(subjectList.get(i).getSubjectId());
            for(int j = 0; j < lecturerList.size(); j++){
                LecturerSubjectDTO lecturerSubjectDTO = new LecturerSubjectDTO();
                lecturerSubjectDTO.setLecturerId(lecturerList.get(j).getUserId());
                lecturerSubjectDTO.setSubjectId(subjectList.get(i).getSubjectId());
                lecturerSubjectDTO.setLecturerName(lecturerList.get(j).getUserName());
                lecturerSubjectDTO.setNickName(lecturerList.get(j).getNickName());
                lecturerSubjectDTOList.add(lecturerSubjectDTO);
            }
        }
        return lecturerSubjectDTOList;
    }

    @Override
    public List<SubjectResponseDTO> getSubjectByMajorId(Long majorId) {
        Major major = majorRepository.findById(majorId).orElseThrow(
                ()-> new ResourceNotFoundException("Major","id",String.valueOf(majorId))
        );
        List<SubjectResponseDTO> subjectResponseDTOList = new ArrayList<>();
       List<String> subjectList = subjectSemesterRepository.findSubjectIdByMajorId(majorId);
       for(int i = 0; i< subjectList.size(); i++){
           Subject subject = subjectRepository.findById(subjectList.get(i)).orElseThrow();
           SubjectResponseDTO subjectResponseDTO = modelMapper.map(subject, SubjectResponseDTO.class);
           subjectResponseDTOList.add(subjectResponseDTO);
       }
        return subjectResponseDTOList;
    }
}
