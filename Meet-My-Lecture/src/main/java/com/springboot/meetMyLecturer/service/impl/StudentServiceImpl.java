package com.springboot.meetMyLecturer.service.impl;


import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotForStudentDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.repository.EmptySlotRepository;
import com.springboot.meetMyLecturer.repository.SlotTimeRepository;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    ModelMapper modelMapper;


    //student search lecturer DONE
    @Override
    public List<LecturerSubjectResponseDTO> searchLecturers(String name) {

        List<User> lecturerList = userRepository.findLecturerByUserName(name);
        if(lecturerList.isEmpty()){
            throw new RuntimeException("There are no lecturers with this name!");
        }

        List<LecturerSubjectResponseDTO> lecturerSubjectResponseDTOList = new ArrayList<>();

        for(int i = 0; i< lecturerList.size(); i++){
            List<Subject> subjectList = subjectRepository.findSubjectsByLecturerId(lecturerList.get(i).getUserId());
            for(int j = 0; j < subjectList.size(); j++){
                LecturerSubjectResponseDTO lecturerSubjectResponseDTO = new LecturerSubjectResponseDTO();
                lecturerSubjectResponseDTO.setLecturerId(lecturerList.get(i).getUserId());
                lecturerSubjectResponseDTO.setNickName(lecturerList.get(i).getNickName());
                lecturerSubjectResponseDTO.setLecturerName(lecturerList.get(i).getUserName());
                lecturerSubjectResponseDTO.setSubjectId(subjectList.get(j).getSubjectId());
                lecturerSubjectResponseDTOList.add(lecturerSubjectResponseDTO);
            }
        }


        return lecturerSubjectResponseDTOList;
    }


    //student view booked slots home page DONE
    @Override
    public List<BookedSlotHomePageDTO> viewBookedSlotHomePage(Long userId) {
        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsByStudent_UserId(userId);


        List<BookedSlotHomePageDTO> bookedSlotHomePageDTOList = emptySlotList.stream().map(
                emptySlot -> {
                    BookedSlotHomePageDTO bookedSlotHomePageDTO = modelMapper.map(emptySlot, BookedSlotHomePageDTO.class);
                    bookedSlotHomePageDTO.setLecturerName(emptySlot.getLecturer().getUserName());
                    bookedSlotHomePageDTO.setSlotId(emptySlot.getSlotTime().getSlotTimeId());
                    return bookedSlotHomePageDTO;
                }).collect(Collectors.toList());

        if (bookedSlotHomePageDTOList.isEmpty()){
            throw new RuntimeException("There are no booked slots!");
        }

        return bookedSlotHomePageDTOList;
    }

    //view booked slot calendar DONE
    @Override
    public List<BookedSlotCalendarDTO> viewBookedSlotCalendar(Long userId) {
        List<EmptySlot> emptySlotList  = emptySlotRepository.findEmptySlotsByStudent_UserId(userId);

        if(emptySlotList.isEmpty()){
            throw  new RuntimeException("There are no booked slots!");
        }

        List<BookedSlotCalendarDTO> bookedSlotCalendarDTOList = emptySlotList.stream().map(
                emptySlot -> {
                    BookedSlotCalendarDTO bookedSlotCalendarDTO = modelMapper.map(emptySlot, BookedSlotCalendarDTO.class);
                    bookedSlotCalendarDTO.setLecturerName(emptySlot.getLecturer().getUserName());
                    bookedSlotCalendarDTO.setSubjectId(emptySlot.getSubject().getSubjectId());
                    return bookedSlotCalendarDTO;
                }
        ).collect(Collectors.toList());


        return bookedSlotCalendarDTOList;
    }

    //student book an empty slot DONE
    @Override
    public BookedSlotCalendarDTO bookEmptySlot(Long emptySlotId, Long studentId, BookSlotDTO bookSlotDTO) {
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
        emptySlot.setBookedDate(Timestamp.valueOf(currentDateTime));

        emptySlotRepository.save(emptySlot);

        return modelMapper.map(emptySlot, BookedSlotCalendarDTO.class);
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

    //view empty slot by lecturerId for student DONE
    @Override
    public List<EmptySlotForStudentDTO> viewEmptySlot(Long lecturerId) {
        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotByLecturer_UserId(lecturerId);

        List<EmptySlotForStudentDTO> emptySlotForStudentDTOList = emptySlotList.stream().map(
                emptySlot -> {
                    return modelMapper.map(emptySlot, EmptySlotForStudentDTO.class);
                }
        ).toList();

        return emptySlotForStudentDTOList;
    }
}


