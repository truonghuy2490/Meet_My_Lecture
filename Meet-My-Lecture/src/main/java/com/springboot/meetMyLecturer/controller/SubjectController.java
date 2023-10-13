package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.entity.Subject;
import com.springboot.meetMyLecturer.repository.SubjectRepository;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {
//    @Autowired
//    SubjectRepository subjectRepository;
//    @Autowired
//    SubjectService subjectService;

//    @GetMapping("/searchSubjects")
//    public ResponseEntity<?> searchSubject(@RequestParam(name = "keyword") String keyword){
//        List<Subject> subjectList = studentService.searchSubject(keyword);
//        try{
//            if(!subjectList.isEmpty()){
//                return ResponseEntity.ok().body(subjectList);
//            }else{
//                return  ResponseEntity.notFound().build();
//            }
//
//        }catch(Exception e){
//            return ResponseEntity.internalServerError().body("Error at:" + e.getMessage());
//        }
//
//    }
}
