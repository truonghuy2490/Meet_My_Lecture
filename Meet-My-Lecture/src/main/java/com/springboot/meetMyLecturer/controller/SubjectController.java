package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectMajorResponseDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.modelDTO.SubjectDTO;
import com.springboot.meetMyLecturer.service.SubjectService;
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

    //DONE-DONE
    @GetMapping("/admin")
    public ResponseEntity<List<SubjectMajorResponseDTO>> getAllSubjects(){
        List<SubjectMajorResponseDTO> subjectList = subjectService.getAllSubjects();
        return new ResponseEntity<>(subjectList, HttpStatus.OK);
    }

    //DONE-DONE
    @PostMapping("/admin")
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody SubjectDTO subjectDTO){
        SubjectResponseDTO subjectResponseDTO = subjectService.createSubject(subjectDTO);
        return new ResponseEntity<>(subjectResponseDTO, HttpStatus.CREATED);
    }

}
