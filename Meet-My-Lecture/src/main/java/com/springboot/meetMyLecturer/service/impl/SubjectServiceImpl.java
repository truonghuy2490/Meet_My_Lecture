package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;


    @Override
    public List<SubjectDTO> searchSubject(String keyword) {
        List<Subject> subjects = subjectRepository.findSubjectBySubjectIdContains(keyword);
        Set<User> users = subjects.stream()
                .flatMap(subject -> subject.getUserSet().stream())
                .collect(Collectors.toSet());

        List<SubjectDTO> subjectDTOS = subjects.stream().map(subject -> {
            SubjectDTO dto = new SubjectDTO();

            dto.setSubjectId(subject.getSubjectId());
            dto.setSubjectName(subject.getSubjectName());

            Set<UserDTO> userDTOs = users.stream().map(user -> {
                UserDTO userDTO = modelMapper.map(user,UserDTO.class);
                return userDTO;
            }).collect(Collectors.toSet());
            dto.setUserDTO(userDTOs);
            return dto;
        }).collect(Collectors.toList());

        return subjectDTOS;
    }

    @Override
    public List<SubjectResponseDTO> getSubjectByLecturerId(int id) {
            List<Subject> subjectList = subjectRepository.findSubjectsByUser_UserId(id);
            List<SubjectResponseDTO> subjectResponseDTOS = subjectList.stream().map(
                    subject -> {
                        SubjectResponseDTO dto = modelMapper.map(subject, SubjectResponseDTO.class);
                        return dto;
                    }).collect(Collectors.toList());
            return subjectResponseDTOS;
    }
}
