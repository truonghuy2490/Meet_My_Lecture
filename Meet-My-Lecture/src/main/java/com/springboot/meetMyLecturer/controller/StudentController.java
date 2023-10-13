package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.modelDTO.UserDTO;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserRepository userRepository;



    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudent(), HttpStatus.OK);
    }

//    @PutMapping
//    public





}
