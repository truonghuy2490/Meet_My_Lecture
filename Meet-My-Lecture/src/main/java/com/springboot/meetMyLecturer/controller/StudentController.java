package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.EmptySlotForStudentDTO;
import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotCalendarDTO;
import com.springboot.meetMyLecturer.ResponseDTO.BookedSlotHomePageDTO;
import com.springboot.meetMyLecturer.modelDTO.BookSlotDTO;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.UserService;
import com.springboot.meetMyLecturer.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    //DONE
    @GetMapping("/bookedSlot/homePage/{userId}")
    public ResponseEntity<List<BookedSlotHomePageDTO>> viewBookedSlotHomePage(@PathVariable Long userId){
            List<BookedSlotHomePageDTO> bookedSlotHomePageDTOList = studentService.viewBookedSlotHomePage(userId);
            return new ResponseEntity<>(bookedSlotHomePageDTOList,HttpStatus.FOUND);
    }

    //DONE
    @GetMapping("bookedSlot/calendar/{userId}")
    public ResponseEntity<List<BookedSlotCalendarDTO>> viewBookedSlotCalendar(@PathVariable Long userId){
        List<BookedSlotCalendarDTO> bookedSlotCalendarDTOList = studentService.viewBookedSlotCalendar(userId);
        return new ResponseEntity<>(bookedSlotCalendarDTOList,HttpStatus.FOUND);
    }


    //DONE
    @PutMapping("/emptySlot/{emptySlotId}/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<BookedSlotCalendarDTO> bookEmptySlot(@PathVariable Long emptySlotId,
                                                               @PathVariable Long studentId,
                                                               @RequestBody BookSlotDTO bookSlotDTO){
        BookedSlotCalendarDTO bookedSlotCalendarDTO = studentService.bookEmptySlot(emptySlotId, studentId, bookSlotDTO);

        return new ResponseEntity<>(bookedSlotCalendarDTO,HttpStatus.OK);
    }

    //DONE
    @PutMapping("{studentId}/bookedSlot/{bookedSlotId}")
    public ResponseEntity<String> deleteBookedSlot (@PathVariable Long bookedSlotId,
                                                    @PathVariable Long studentId){
        String result = studentService.deleteBookedSlot(bookedSlotId,studentId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    //DONE
    @GetMapping("/emptySlot/lecturer/{lecturerId}")
    public ResponseEntity<List<EmptySlotForStudentDTO>> viewEmptySlot (@PathVariable Long lecturerId){
        List<EmptySlotForStudentDTO> emptySlotDTOList = studentService.viewEmptySlot(lecturerId);
        return new ResponseEntity<>(emptySlotDTOList, HttpStatus.FOUND);
    }










}
