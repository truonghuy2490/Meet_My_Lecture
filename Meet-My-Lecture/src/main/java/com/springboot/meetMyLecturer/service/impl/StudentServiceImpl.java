package com.springboot.meetMyLecturer.service.impl;


import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectResponseDTO;
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
                    return emptySlotDTO;
                }).collect(Collectors.toList());

        return emptySlotDTOList;
    }

    //student book an empty slot
    @Override
    public EmptySlotDTO bookEmptySlot(Long emptySlotId, Long studentId, String subjectId) {
        EmptySlot emptySlot = emptySlotRepository.findById(emptySlotId).orElseThrow(
                () -> new ResourceNotFoundException("Emoty Slot", "id", String.valueOf(emptySlotId))
        );

        User student = userRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId))
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );

        emptySlot.setStudent(student);
        emptySlot.setSubject(subject);

        EmptySlotDTO emptySlotDTO = modelMapper.map(emptySlot, EmptySlotDTO.class);

        emptySlotRepository.save(emptySlot);

        return emptySlotDTO;
    }

    @Override
    public String deleteBookedSlot(Long bookedSlotId) {
        emptySlotRepository.deleteById(bookedSlotId);
        return "This booked slot has been deleted!";
    }

}


