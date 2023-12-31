package com.springboot.meetMyLecturer.service.impl;

import com.springboot.meetMyLecturer.ResponseDTO.UserRegisterResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.TeachingScheduleDTO;
import com.springboot.meetMyLecturer.repository.SlotTimeRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.TeachingScheduleRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.ImportTeachingScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    SlotTimeRepository slotTimeRepository;
    @Override
    public List<TeachingScheduleDTO> getTeachingScheduleByLectureId(Long lecturerId) {

        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecture","id",String.valueOf(lecturerId))
        );

        List<TeachingSchedule> teachingSchedules = teachingScheduleRepository.getTeachingScheduleListByLecturer_UserId(lecturerId);

        if(teachingSchedules.isEmpty()){
            throw new RuntimeException("There no schedule on this lecturer id");
        }

        return teachingSchedules.stream().map(
                teachingSchedule -> modelMapper.map(teachingSchedule, TeachingScheduleDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public Set<TeachingScheduleDTO> createTeachingSchedule(Set<TeachingScheduleDTO> teachingScheduleDTO, Long lecturerId) {
        User lecturer = userRepository.findById(lecturerId).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer","id",String.valueOf(lecturerId))
        );

        return teachingScheduleDTO.stream().map(
                t -> {

                    Subject subject = subjectRepository.findById(t.getSubjectId()).orElseThrow(
                            () -> new ResourceNotFoundException("Subject", "id", t.getSubjectId())
                    );

                    SlotTime slot = slotTimeRepository.findById(t.getSlotTimeId()).orElseThrow(
                            ()-> new ResourceNotFoundException("Slot", "id", String.valueOf(t.getSlotTimeId()))
                    );

                    Long checkId = teachingScheduleRepository.findIdByDate(t.getDate());
                    if(checkId != null){
                        throw new RuntimeException("There is already existed this date: " + t.getDate());
                    }

                    TeachingSchedule teachingSchedule = new TeachingSchedule();
                    teachingSchedule.setSubject(subject);
                    teachingSchedule.setSlot(slot);
                    teachingSchedule.setLecturer(lecturer);
                    teachingSchedule.setRoomId(t.getRoomId());
                    teachingSchedule.setDate(t.getDate());
                    teachingSchedule.setStatus(Constant.PUBLIC);


                    teachingScheduleRepository.save(teachingSchedule);

                    return modelMapper.map(teachingSchedule, TeachingScheduleDTO.class);
                }
        ).collect(Collectors.toSet());
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

    @Override
    public String setStatusForTeachingSchedule(Long lecturerId, String status) {

        User lecturer = userRepository.findUserByUserIdAndStatus(lecturerId, Constant.OPEN);
        if(lecturer == null) throw new ResourceNotFoundException("Lecturer","id", String.valueOf(lecturerId));

        List<Long> teachingScheduleIdSet = new ArrayList<>();

        if(status.equalsIgnoreCase(Constant.PUBLIC)){
            teachingScheduleIdSet = teachingScheduleRepository.getIdByLecturerIdAndStatus(lecturerId, Constant.PRIVATE);

            for (Long t: teachingScheduleIdSet){
                TeachingSchedule teachingSchedule = teachingScheduleRepository.findById(t).orElseThrow(
                        ()-> new ResourceNotFoundException("TeachingSchedule","id", String.valueOf(t))
                );

                teachingSchedule.setStatus(Constant.PUBLIC);
                teachingScheduleRepository.save(teachingSchedule);
            }
            return "Everything is PUBLIC";
        }else if(status.equalsIgnoreCase(Constant.PRIVATE)){
            teachingScheduleIdSet = teachingScheduleRepository.getIdByLecturerIdAndStatus(lecturerId, Constant.PUBLIC);

            for (Long t: teachingScheduleIdSet){
                TeachingSchedule teachingSchedule = teachingScheduleRepository.findById(t).orElseThrow(
                        ()-> new ResourceNotFoundException("TeachingSchedule","id", String.valueOf(t))
                );

                teachingSchedule.setStatus(Constant.PRIVATE);
                teachingScheduleRepository.save(teachingSchedule);
            }
            return "Everything is PRIVATE";
        }else{
            throw new RuntimeException("Invalid status.");
        }
    }

}
