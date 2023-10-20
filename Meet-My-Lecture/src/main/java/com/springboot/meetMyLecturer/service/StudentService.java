package com.springboot.meetMyLecturer.service;


import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;

import java.util.List;

public interface StudentService {

    List<LecturerSubjectDTO> searchLecturers (String name);

    List<BookedSlotHomePageDTO> viewBookedSlotHomePage(Long userId);

    List<BookedSlotCalendarDTO> viewBookedSlotCalendar(Long userId);

    BookedSlotCalendarDTO bookEmptySlot(Long emptySlotId, Long studentId, BookSlotDTO bookSlotDTO);

    String deleteBookedSlot(Long bookedSlotId, Long studentId);

}
