package com.springboot.meetMyLecturer.service;


import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.modelDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.modelDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;

import java.util.List;

public interface StudentService {

    List<UserDTO> searchLecturers (String name);

    List<BookedSlotHomePageDTO> viewBookedSlotHomePage(Long userId);

    List<BookedSlotCalendarDTO> viewBookedSlotCalendar(Long userId);

    BookedSlotCalendarDTO bookEmptySlot(Long emptySlotId, Long studentId, String subjectId, EmptySlot emptySlot);

    String deleteBookedSlot(Long bookedSlotId, Long studentId);

}
