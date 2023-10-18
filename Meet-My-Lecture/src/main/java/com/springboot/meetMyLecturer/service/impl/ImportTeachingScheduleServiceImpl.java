package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.SlotRepository;
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
    @Autowired
    SlotRepository slotRepository;
    @Override
    public List<TeachingScheduleDTO> getTeachingScheduleByLectureId(Long lecturerId) {
        // retrieve user
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecture","id",String.valueOf(lecturerId))
        );
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        // retrieve schedule
        List<TeachingSchedule> teachingSchedules = teachingScheduleRepository.getTeachingScheduleListByLecturer_UserId(lecturerId);

        if(teachingSchedules.isEmpty()){
            throw new RuntimeException("There no schedule on this lecturer id");
        }

        return  teachingSchedules.stream().map(
                teachingSchedule -> modelMapper.map(teachingSchedule, TeachingScheduleDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public TeachingScheduleDTO createTeachingSchedule(TeachingSchedule teachingSchedule, Long lecturerId) {

        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );
        teachingSchedule.setLecturer(user); // set entity

        String subjectId = teachingSchedule.getSubject().getSubjectId();

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );
        teachingSchedule.setSubject(subject); // set entity

        long slotId = teachingSchedule.getSlot().getSlotId();

        Slot slot = slotRepository.findById(slotId).orElseThrow(
                ()-> new ResourceNotFoundException("Slot","id",String.valueOf(slotId))
        );
        teachingSchedule.setSlot(slot); // set entity

        TeachingScheduleDTO teachingScheduleDTO = modelMapper.map(teachingSchedule, TeachingScheduleDTO.class);

        teachingScheduleDTO.setRoomId(teachingSchedule.getRoomId());

        teachingScheduleRepository.save(
                modelMapper.map(teachingSchedule, TeachingSchedule.class)
        );
        return teachingScheduleDTO;
    }

    @Override
    public void deleteSchedule(Long lecturerId, Long scheduleId) {
        User user = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );
        TeachingSchedule teachingSchedules = teachingScheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ResourceNotFoundException("Schedule","id",String.valueOf(scheduleId))
        );
        if(!user.getUserId().equals(teachingSchedules.getLecturer().getUserId())){
            throw new RuntimeException("there no schedule for this lecture");
        }
        teachingScheduleRepository.delete(teachingSchedules);
    }

}
