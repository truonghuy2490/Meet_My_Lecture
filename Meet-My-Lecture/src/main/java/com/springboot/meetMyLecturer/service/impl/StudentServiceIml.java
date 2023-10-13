package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Subject;
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
public class StudentServiceIml implements StudentService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllStudent() {
        List<User> students = userRepository.findAll();
        if (students.isEmpty()) {
            return null;
        }
            List<UserDTO> userDTOS = students.stream().map(
                    user -> {
                        UserDTO dto = modelMapper.map(students, UserDTO.class);
                        return dto;
                    }
            ).collect(Collectors.toList());;

        return userDTOS;
    }
}


