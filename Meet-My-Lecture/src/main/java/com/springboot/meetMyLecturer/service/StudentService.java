package com.springboot.meetMyLecturer.service;


import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;

import java.util.List;

public interface StudentService {

    List<LecturerSubjectResponseDTO> searchLecturers (String name);

    List<EmptySlotResponseDTO> viewBookedSlotHomePage(Long studentId);

    List<EmptySlotResponseDTO> viewBookedSlotCalendar(Long lecturerId);

    EmptySlotResponseDTO bookEmptySlot(Long emptySlotId, Long studentId, BookSlotDTO bookSlotDTO);

    String deleteBookedSlot(Long bookedSlotId, Long studentId);

    List<LecturerSubjectResponseDTO> recommendRelatedCourses(Long studentId);

    Long chooseMajor(Long studentId, Long majorId);

}
