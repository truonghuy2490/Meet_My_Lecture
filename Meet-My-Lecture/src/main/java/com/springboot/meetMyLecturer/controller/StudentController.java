package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.entity.User;
import com.springboot.meetMyLecturer.repository.UserRepository;
import com.springboot.meetMyLecturer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/searchSubjects")
    public ResponseEntity<?> searchSubject(@RequestParam(name = "keyword") String keyword){
        List<Subject> subjectList = studentService.searchSubject(keyword);
        try{
            return ResponseEntity.ok().body(subjectList);

        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Error at:" + e.getMessage());
        }

    }

    @GetMapping("/searchLecturers")
    public ResponseEntity<?> searchLecturer (@RequestParam String name){
        try{

            List<User> lecturerList = userRepository.findUser(name);

            return ResponseEntity.ok().body(lecturerList);

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error at:" + e.getMessage());
        }
    }

}
