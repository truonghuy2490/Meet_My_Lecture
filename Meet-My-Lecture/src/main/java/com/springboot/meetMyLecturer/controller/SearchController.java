package com.springboot.meetMyLecturer.controller;

import com.springboot.meetMyLecturer.ResponseDTO.LecturerSubjectDTO;
import com.springboot.meetMyLecturer.ResponseDTO.SubjectResponseDTO;
import com.springboot.meetMyLecturer.service.StudentService;
import com.springboot.meetMyLecturer.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student/search")
public class SearchController {

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;

    //DONE
    @GetMapping("/lecturer")
    public ResponseEntity<List<LecturerSubjectDTO>> searchLecturerByLecturerName(@RequestParam String name){

            List<LecturerSubjectDTO> lecturerSubjectDTOList = studentService.searchLecturers(name);
             return new ResponseEntity<>(lecturerSubjectDTOList,HttpStatus.FOUND);
    }

    //DONE
    @GetMapping("/subject")
    public ResponseEntity<List<LecturerSubjectDTO> > searchSubjectBySubjectId(@RequestParam String keyword){
        List<LecturerSubjectDTO> subjectList = subjectService.searchSubject(keyword);
        return new ResponseEntity<>(subjectList, HttpStatus.FOUND);
    }

    @GetMapping("/major/{majorId}")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjectsByMajorId(@PathVariable Long majorId){
        List<SubjectResponseDTO> subjectResponseDTOList = subjectService.getSubjectByMajorId(majorId);
        return new ResponseEntity<>(subjectResponseDTOList,HttpStatus.FOUND);
    }

}
