package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.service.SubjectService;
import com.springboot.meetMyLecturer.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<LecturerSubjectDTO> > searchSubject(@PathVariable String keyword){
        List<LecturerSubjectDTO> subjectList = subjectService.searchSubject(keyword);
       return new ResponseEntity<>(subjectList, HttpStatus.FOUND);
    }

}

