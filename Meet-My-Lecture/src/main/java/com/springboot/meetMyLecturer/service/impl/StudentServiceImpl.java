package com.springboot.meetMyLecturer.service.impl;


import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.entity.SlotTime;
import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.exception.ResourceNotFoundException;
import com.springboot.meetMyLecturer.modelDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.modelDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
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
import java.time.LocalTime;
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
    public List<UserDTO> searchLecturers(String name) {

        List<User> lecturerList = userRepository.findLecturerByUserName(name);
        if(lecturerList.isEmpty()){
            throw new RuntimeException("There are no lecturers with this name!");
        }
        List<UserDTO> userDTOList = lecturerList.stream().map(
                user -> {UserDTO dto = modelMapper.map(user, UserDTO.class);
                return dto;
                }).collect(Collectors.toList());
        return userDTOList;
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

        List<BookedSlotCalendarDTO> bookedSlotCalendarDTOList = emptySlotList.stream().map(
                emptySlot -> {
                    BookedSlotCalendarDTO bookedSlotCalendarDTO = modelMapper.map(emptySlot, BookedSlotCalendarDTO.class);
                    bookedSlotCalendarDTO.setLecturerName(emptySlot.getLecturer().getUserName());
                    bookedSlotCalendarDTO.setSubjectId(emptySlot.getSubject().getSubjectId());
                    return bookedSlotCalendarDTO;
                }
        ).collect(Collectors.toList());

        if(bookedSlotCalendarDTOList.isEmpty()){
            throw  new RuntimeException("There are no booked slots!");
        }

        return bookedSlotCalendarDTOList;
    }

    //student book an empty slot DONE
    @Override
    public BookedSlotCalendarDTO bookEmptySlot(Long emptySlotId, Long studentId, String subjectId, EmptySlot emptySlot) {
        EmptySlot emptySlotDB = emptySlotRepository.findById(emptySlotId).orElseThrow(
                () -> new ResourceNotFoundException("Empty Slot", "id", String.valueOf(emptySlotId))
        );

        User student = userRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student", "id", String.valueOf(studentId))
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new ResourceNotFoundException("Subject", "id", subjectId)
        );

        LocalDateTime currentDateTime = LocalDateTime.now();


        if(emptySlotDB.getCode() != null){
            if(!emptySlotDB.getCode().equals(emptySlot.getCode())){
                throw new RuntimeException("Wrong code:" + emptySlot.getCode());
            }
        }

        emptySlotDB.setStudent(student);
        emptySlotDB.setSubject(subject);
        emptySlotDB.setDescription(emptySlot.getDescription());
        emptySlotDB.setStatus("Booked");
        emptySlotDB.setBookedDate(Timestamp.valueOf(currentDateTime));

        emptySlotRepository.save(emptySlotDB);

        BookedSlotCalendarDTO bookedSlotCalendarPageDTO = modelMapper.map(emptySlotDB, BookedSlotCalendarDTO.class);


        bookedSlotCalendarPageDTO.setSubjectId(subjectId);
        bookedSlotCalendarPageDTO.setLecturerName(emptySlotDB.getLecturer().getUserName());

        return bookedSlotCalendarPageDTO;
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

        emptySlotRepository.save(emptySlot);

        return "This booked slot has been deleted!";
    }
}


