package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.service.SubjectService;
import com.springboot.meetMyLecturer.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchSubject(@PathVariable String keyword){
        List<SubjectDTO> subjectList = subjectService.searchSubject(keyword);

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

}

