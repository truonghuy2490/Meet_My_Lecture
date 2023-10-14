package com.springboot.meetMyLecturer.service.impl;


import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;


    @Override
    public List<UserDTO> searchLecturers(String name) {

        List<User> lecturerList = userRepository.findLecturerByUserName(name);
        if(lecturerList.isEmpty()){
            return null;
        }
        List<UserDTO> userDTOList = lecturerList.stream().map(
                user -> {UserDTO dto = modelMapper.map(user, UserDTO.class);
                return dto;
                }).collect(Collectors.toList());
        return userDTOList;
    }

}


