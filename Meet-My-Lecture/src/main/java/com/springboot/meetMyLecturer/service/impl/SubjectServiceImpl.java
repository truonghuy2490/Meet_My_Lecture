package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
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
                UserDTO userDTO = new UserDTO();
                userDTO.setUserId(user.getUserId());
                userDTO.setUserName(user.getUserName());
                return userDTO;
            }).collect(Collectors.toSet());
            dto.setUserDTO(userDTOs);
            return dto;
        }).collect(Collectors.toList());

        return subjectDTOS;

    }
}
