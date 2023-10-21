package com.springboot.meetMyLecturer.service.impl;


import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.SubjectLecturerStudent;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.repository.*;
import com.springboot.meetMyLecturer.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    ModelMapper modelMapper;

    @Autowired
    SubjectLecturerStudentRepository subjectLecturerStudentRepository;


    //student search lecturer DONE
    @Override
    public List<LecturerSubjectResponseDTO> searchLecturers(String name) {

        List<User> lecturerList = userRepository.findLecturerByUserName(name);
        if(lecturerList.isEmpty()){
            throw new RuntimeException("There are no lecturers with this name!");
        }

        return lecturerList.stream()
                .flatMap(lecturer -> subjectRepository.findSubjectsByLecturerId(lecturer.getUserId()).stream()
                        .map(subject -> {
                            LecturerSubjectResponseDTO dto = new LecturerSubjectResponseDTO();
                            dto.setLecturerId(lecturer.getUserId());
                            dto.setUnique(lecturer.getUnique());
                            dto.setLecturerName(lecturer.getUserName());
                            dto.setSubjectId(subject.getSubjectId());
                            return dto;
                        }))
                .collect(Collectors.toList());
    }


    //student view booked slots home page DONE
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

    //view booked slot calendar DONE
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

    //student book an empty slot DONE
    @Override
    public EmptySlotResponseDTO bookEmptySlot(Long emptySlotId, Long studentId, BookSlotDTO bookSlotDTO) {
        EmptySlot emptySlot = emptySlotRepository.findById(emptySlotId).orElseThrow(
                () -> new ResourceNotFoundException("Empty Slot", "id", String.valueOf(emptySlotId))
        );

        User student = userRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId))
        );
        Subject subject = subjectRepository.findById(bookSlotDTO.getSubjectId()).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", bookSlotDTO.getSubjectId())
        );

        LocalDateTime currentDateTime = LocalDateTime.now();

        if(emptySlot.getCode() != null){
            if(!emptySlot.getCode().equals(bookSlotDTO.getCode())){
                throw new RuntimeException("Wrong code:" + bookSlotDTO.getCode());
            }
        }

        emptySlot.setStudent(student);
        emptySlot.setSubject(subject);
        emptySlot.setDescription(bookSlotDTO.getDescription());
        emptySlot.setStatus("Booked");
        emptySlot.setBookedDate(currentDateTime);

        emptySlotRepository.save(emptySlot);

        return modelMapper.map(emptySlot, EmptySlotResponseDTO.class);
    }

    //student delete bookedSlot DONE
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

        emptySlotRepository.save(emptySlot);

        return "This booked slot has been deleted!";
    }

    // recommend related courses for student DONE
    @Override
    public List<LecturerSubjectResponseDTO> recommendRelatedCourses(Long studentId) {
        User student = userRepository.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException("Student","id",String.valueOf(studentId))
        );

        List<SubjectLecturerStudent> subjectLecturerStudentList = subjectLecturerStudentRepository
                                .searchSubjectLecturerStudentsByStudent_UserId(student.getUserId());
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
}


