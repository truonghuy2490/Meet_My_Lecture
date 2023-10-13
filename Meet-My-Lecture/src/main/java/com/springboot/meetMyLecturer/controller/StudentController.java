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
            if(!subjectList.isEmpty()){
                return ResponseEntity.ok().body(subjectList);
            }else{
                return  ResponseEntity.notFound().build();
            }

        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Error at:" + e.getMessage());
        }

    }

    @GetMapping("/searchLecturers")
    public ResponseEntity<?> searchLecturer (@RequestParam String name){
        try{

            List<User> lecturerList = userRepository.findUserByUserName(name);

            if(!lecturerList.isEmpty()){
                return ResponseEntity.ok().body(lecturerList);
            }else{
                return  ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Error at:" + e.getMessage());
        }
    }

}
