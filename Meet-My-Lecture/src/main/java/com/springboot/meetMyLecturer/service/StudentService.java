package com.springboot.meetMyLecturer.service;


import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotForStudentDTO;
import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;

import java.util.List;

public interface StudentService {

    List<LecturerSubjectResponseDTO> searchLecturers (String name);

    List<BookedSlotHomePageDTO> viewBookedSlotHomePage(Long userId);

    List<BookedSlotCalendarDTO> viewBookedSlotCalendar(Long lecturerId);

    BookedSlotCalendarDTO bookEmptySlot(Long emptySlotId, Long studentId, BookSlotDTO bookSlotDTO);

    String deleteBookedSlot(Long bookedSlotId, Long studentId);

    List<EmptySlotForStudentDTO> viewEmptySlot(Long lecturerId);

    List<LecturerSubjectResponseDTO> recommendRelatedCourses(Long studentId);

}
