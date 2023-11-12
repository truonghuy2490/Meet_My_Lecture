package com.springboot.meetMyLecturer.service.impl;


import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.constant.Constant;
import com.springboot.meetMyLecturer.entity.*;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.StudentService;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    SlotTimeRepository slotTimeRepository;

    @Autowired
    MajorRepository majorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SubjectLecturerStudentRepository subjectLecturerStudentRepository;


    //student search lecturer DONE
    @Override
    public List<LecturerSubjectResponseDTO> searchLecturers(String name) {

        List<User> lecturerList = userRepository.findLecturerByUserNameAndStatus(name, Constant.OPEN);
        if(lecturerList.isEmpty()){
            throw new RuntimeException("There are no lecturers with this name!");
        }

        return lecturerList.stream()
                .flatMap(lecturer -> subjectRepository.findSubjectsByLecturerIdAndStatus(lecturer.getUserId(), Constant.OPEN).stream()
                        .map(subject -> {
                            LecturerSubjectResponseDTO dto = new LecturerSubjectResponseDTO();
                            dto.setLecturerId(lecturer.getUserId());
                            dto.setUnique(lecturer.getUnique());
                            dto.setLecturerName(lecturer.getUserName());
                            dto.setSubjectId(subject.getSubjectId());
                            dto.setSubjectName(subject.getSubjectName());
                            return dto;
                        }))
                .collect(Collectors.toList());
    }


    //student view booked slots home page DONE - DONE
    @Override
    public List<EmptySlotResponseDTO> viewBookedSlotHomePage(Long studentId) {
        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsByStudent_UserId(studentId);

        if (emptySlotList.isEmpty()){
            throw new RuntimeException("There are no booked slots!");
        }

        return emptySlotList.stream().map(
                emptySlot -> modelMapper.map(emptySlot, EmptySlotResponseDTO.class)
        ).collect(Collectors.toList());
    }

    //view booked slot calendar DONE - DONE
    @Override
    public List<EmptySlotResponseDTO> viewBookedSlotCalendar(Long lecturerId) {
        List<EmptySlot> emptySlotList  = emptySlotRepository.findEmptySlotsByStudent_UserId(lecturerId);

        if(emptySlotList.isEmpty()){
            throw  new RuntimeException("There are no booked slots!");
        }

        return emptySlotList.stream().map(
                emptySlot ->  modelMapper.map(emptySlot, EmptySlotResponseDTO.class)
        ).collect(Collectors.toList());
    }

    //student book an empty slot DONE - DONE
    @Override
    public EmptySlotResponseDTO bookEmptySlot(Long emptySlotId, Long studentId, BookSlotDTO bookSlotDTO) {
        EmptySlot emptySlot = emptySlotRepository.findById(emptySlotId).orElseThrow(
                () -> new ResourceNotFoundException("Empty Slot", "id", String.valueOf(emptySlotId))
        );

        if(emptySlot.getStatus().equals("EXPIRED") || emptySlot.getStatus().equals("BOOKED")){
            throw new RuntimeException("This empty slot is not available.");
        }

        User student = userRepository.findUserByUserIdAndStatus(studentId, Constant.OPEN);
        if(student == null) throw new RuntimeException("This student is not existed.");

        Subject subject = subjectRepository.findSubjectBySubjectIdAndStatus(bookSlotDTO.getSubjectId(),Constant.OPEN);
        if(subject == null){throw new RuntimeException("This subject has been closed.");}

        LocalDateTime currentDateTime = LocalDateTime.now();

        if(emptySlot.getCode() != null){
            if(!emptySlot.getCode().equals(bookSlotDTO.getCode())){
                throw new RuntimeException("Wrong code:" + bookSlotDTO.getCode());
            }
        }

        emptySlot.setStudent(student);
        emptySlot.setSubject(subject);
        emptySlot.setDescription(bookSlotDTO.getDescription());
        emptySlot.setStatus("BOOKED");
        emptySlot.setBookedDate(currentDateTime);

        emptySlotRepository.save(emptySlot);

        return modelMapper.map(emptySlot, EmptySlotResponseDTO.class);
    }

    //student delete bookedSlot DONE - DONE
    @Override
    public String deleteBookedSlot(Long bookedSlotId, Long studentId) {

        EmptySlot emptySlot = emptySlotRepository.findById(bookedSlotId).orElseThrow(
                () -> new ResourceNotFoundException("Booked Slot","id",String.valueOf(bookedSlotId))
        );

        if(!emptySlot.getStudent().getUserId().equals(studentId)){
            throw  new RuntimeException("This student does not book this slot.");
        }

        emptySlot.setStudent(null);
        emptySlot.setSubject(null);
        emptySlot.setBookedDate(null);
        emptySlot.setStatus(Constant.OPEN);

        emptySlotRepository.save(emptySlot);

        return "This booked slot has been deleted!";
    }

    // recommend related courses for student DONE - DONE
    @Override
    public List<LecturerSubjectResponseDTO> recommendRelatedCourses(Long studentId) {
        User student = userRepository.findUserByUserIdAndStatus(studentId, Constant.OPEN);
        if(student == null) throw new RuntimeException("This student is not existed.");

        List<SubjectLecturerStudent> subjectLecturerStudentList = subjectLecturerStudentRepository
                                .searchSubjectLecturerStudentsByStudent_UserIdAndStatus(student.getUserId(), Constant.OPEN);
        if(subjectLecturerStudentList.isEmpty()){
            throw new RuntimeException("There are no related courses with this student Id:" + studentId);
        }

        return subjectLecturerStudentList.stream().map(
                subjectLecturerStudent -> {
                    LecturerSubjectResponseDTO lecturerSubjectResponseDTO = modelMapper.map(subjectLecturerStudent, LecturerSubjectResponseDTO.class);
                    lecturerSubjectResponseDTO.setUnique(subjectLecturerStudent.getLecturer().getUnique());
                    return lecturerSubjectResponseDTO;
                }).collect(Collectors.toList());
    }

    //choose major for student DONE-DONE
    @Override
    public Long chooseMajor(Long studentId, Long majorId) {

        User student = userRepository.findUserByUserIdAndStatus(studentId, Constant.OPEN);
        if(student == null) throw new ResourceNotFoundException("Student","id", String.valueOf(studentId));

        Major major = majorRepository.findMajorByMajorIdAndStatus(majorId, Constant.OPEN);
        if(major == null) throw new ResourceNotFoundException("Major","id",String.valueOf(majorId));

        if(student.getMajor().equals(major)){
            throw new RuntimeException("You already have this major.");
        }
        student.setMajor(major);

        userRepository.save(student);

        return majorId;
    }
}


