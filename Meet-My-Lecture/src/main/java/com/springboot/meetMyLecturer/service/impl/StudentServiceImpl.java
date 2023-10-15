package com.springboot.meetMyLecturer.service.impl;


import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.EmptySlotRepository;
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
    UserRepository userRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    EmptySlotRepository emptySlotRepository;


    @Autowired
    ModelMapper modelMapper;


    //student search lecturer
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


    //student view booked slots
    @Override
    public List<EmptySlotDTO> viewBookedSlot(Long userId) {
        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsByUser_UserId(userId);

        List<EmptySlotDTO> emptySlotDTOList = emptySlotList.stream().map(
                emptySlot -> {
                    EmptySlotDTO emptySlotDTO = modelMapper.map(emptySlot,EmptySlotDTO.class);
                    UserDTO lecturerDTO = modelMapper.map(emptySlot.getLecturer(),UserDTO.class);
                    UserDTO studentDTO = modelMapper.map(emptySlot.getStudent(),UserDTO.class);
                    emptySlotDTO.setStudent(studentDTO);
                    emptySlotDTO.setLecturer(lecturerDTO);
                    return emptySlotDTO;
                }).collect(Collectors.toList());

        return emptySlotDTOList;
    }

}


