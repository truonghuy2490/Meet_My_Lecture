package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.TeachingSchedule;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.TeachingScheduleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.ImportTeachingScheduleService;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportTeachingScheduleServiceImpl implements ImportTeachingScheduleService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TeachingScheduleRepository teachingScheduleRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Override
    public List<TeachingScheduleDTO> getTeachingScheduleByLectureId(Long lecturerId) {
        // retrieve user
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecture","id",String.valueOf(lecturerId))
        );
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        // retrieve schedule
        List<TeachingSchedule> teachingSchedules = teachingScheduleRepository.getTeachingScheduleByLecturer_UserId(lecturerId);

        if(teachingSchedules.isEmpty()){
            throw new RuntimeException("There no schedule on this lecturer id");
        }

        return  teachingSchedules.stream().map(
                teachingSchedule -> modelMapper.map(teachingSchedule, TeachingScheduleDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public TeachingScheduleDTO createTeachingSchedule(TeachingSchedule teachingSchedule, Long lecturerId) {
        TeachingScheduleDTO teachingScheduleDTO = modelMapper.map(teachingSchedule, TeachingScheduleDTO.class);
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );
        teachingSchedule.setLecturer(user); // set entity

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        teachingScheduleDTO.setLecturer(userDTO); // set dto

        String subjectId = teachingSchedule.getSubject().getSubjectId();

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );
        teachingSchedule.setSubject(subject); // set entity

        SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
        teachingScheduleDTO.setSubject(subjectDTO); // set dto

        teachingScheduleRepository.save(
                modelMapper.map(teachingSchedule, TeachingSchedule.class)
        );
        return teachingScheduleDTO;
    }

}
