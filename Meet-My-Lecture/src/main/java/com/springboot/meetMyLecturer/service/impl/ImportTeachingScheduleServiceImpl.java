package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.TeachingSchedule;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
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
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecture","id",String.valueOf(lecturerId))
        );
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        List<TeachingSchedule> teachingScheduleDBs = teachingScheduleRepository.getTeachingScheduleByLecturer_UserId(lecturerId);
        if(teachingScheduleDBs.isEmpty()){
            throw new RuntimeException("There no teaching schedule in this id");
        }

        return teachingScheduleDBs.stream().map(
                teachingSchedule -> modelMapper.map(teachingSchedule, TeachingScheduleDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public TeachingScheduleDTO createTeachingSchedule(TeachingSchedule teachingSchedule, Long lecturerId, String subjectId) {
        TeachingScheduleDTO teachingScheduleDTO = modelMapper.map(teachingSchedule, TeachingScheduleDTO.class);
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );
        SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
        teachingScheduleDTO.setSubject(subjectDTO);
        teachingScheduleDTO.setLecture(modelMapper.map(user, UserDTO.class));

        TeachingSchedule scheduleResponse = teachingScheduleRepository.save(modelMapper.map(teachingScheduleDTO, TeachingSchedule.class));

        return modelMapper.map(scheduleResponse, TeachingScheduleDTO.class);
    }
}
