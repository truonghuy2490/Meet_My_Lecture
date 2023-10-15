package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.impl.StudentServiceImpl;
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
    StudentServiceImpl studentService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/bookedSlot/{userId}")
    public ResponseEntity<List<EmptySlotDTO>> viewBookedSlot(@PathVariable Long userId){
            List<EmptySlotDTO> emptySlotDTOList = studentService.viewBookedSlot(userId);
            return new ResponseEntity<>(emptySlotDTOList,HttpStatus.FOUND);
    }







}